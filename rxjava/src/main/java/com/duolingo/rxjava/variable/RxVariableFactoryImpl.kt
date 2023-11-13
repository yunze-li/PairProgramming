package com.duolingo.rxjava.variable

import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.subjects.CompletableSubject

/** Default implementation of our [RxVariable.Factory]. */
internal class RxVariableFactoryImpl(
    private val schedulerProvider: SchedulerProvider,
) : RxVariable.Factory {

  override fun <T : Any> create(initialValue: T): RxVariable<T> =
    ProcessorRxVariable(initialValue, schedulerProvider.computation)

  /** [RxVariable] backed by a [io.reactivex.rxjava3.processors.FlowableProcessor]. */
  private class ProcessorRxVariable<T : Any>(
    private val initialValue: T,
    private val scheduler: Scheduler,
  ) : RxVariable<T> {

    private val updates =
      PublishProcessor.create<Pair<(T) -> T, CompletableSubject>>().toSerialized()

    private val state = BehaviorProcessor.create<Pair<T, CompletableSubject>>()

    private val ensureInitialized by lazy {
      state.onBackpressureBuffer().subscribe { (_, updateCompletion) ->
        updateCompletion.onComplete()
      }

      val initialValue = initialValue to CompletableSubject.create()
      updates
        .onBackpressureBuffer()
        .observeOn(scheduler)
        .scan(initialValue) { (value, _), (update, updateCompletion) ->
          update(value) to updateCompletion
        }
        .subscribe(state)
    }

    override fun observe(): Flowable<T> {
      ensureInitialized
      return state.subscribeOn(scheduler).onBackpressureLatest().map { (value, _) -> value }
    }

    override fun update(update: (T) -> T): Completable {
      ensureInitialized
      val updateCompletion = CompletableSubject.create()
      updates.onNext(update to updateCompletion)
      return updateCompletion
    }
  }
}
