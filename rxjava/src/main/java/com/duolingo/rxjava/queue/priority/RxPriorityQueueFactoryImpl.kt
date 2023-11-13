package com.duolingo.rxjava.queue.priority

import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.subjects.CompletableSubject
import java.util.concurrent.ConcurrentLinkedQueue

/** Default implementation of our [RxPriorityQueue.Factory]. */
internal class RxPriorityQueueFactoryImpl(
    private val schedulerProvider: SchedulerProvider,
) : RxPriorityQueue.Factory {

  override fun create(parallelism: Int): RxPriorityQueue =
    RxPriorityQueueImpl(parallelism, schedulerProvider.computation)

  private class RxPriorityQueueImpl(
    private val parallelism: Int,
    private val scheduler: Scheduler,
  ) : RxPriorityQueue {

    private val ensureQueueStarted by lazy {
      enqueuedProcessor
        .onBackpressureBuffer()
        .observeOn(scheduler)
        .flatMapCompletable(
          {
            val task =
              highPriority.poll()
                ?: mediumPriority.poll() ?: lowPriority.poll()
                  ?: error("Received enqueued signal but no task queued")
            task.started.onComplete()
            task.finished
          },
          false,
          parallelism,
        )
        .onErrorComplete()
        .subscribe()
    }

    private val highPriority = ConcurrentLinkedQueue<PrioritizedTask>()
    private val mediumPriority = ConcurrentLinkedQueue<PrioritizedTask>()
    private val lowPriority = ConcurrentLinkedQueue<PrioritizedTask>()
    private val enqueuedProcessor = PublishProcessor.create<Unit>().toSerialized()

    override fun schedule(priority: Priority, completable: Completable): Completable =
      Completable.defer {
        ensureQueueStarted
        val task =
          PrioritizedTask(
            started = CompletableSubject.create(),
            finished = CompletableSubject.create()
          )
        queue(priority).add(task)
        enqueuedProcessor.onNext(Unit)
        task
          .started
          .andThen(completable)
          .doOnDispose { task.finished.onComplete() }
          .doAfterTerminate { task.finished.onComplete() }
      }

    private fun queue(priority: Priority) =
      when (priority) {
        Priority.LOW -> lowPriority
        Priority.MEDIUM -> mediumPriority
        Priority.HIGH -> highPriority
      }

    private data class PrioritizedTask(
      val started: CompletableSubject,
      val finished: CompletableSubject,
    )
  }
}
