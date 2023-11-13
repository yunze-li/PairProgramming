package com.duolingo.rxjava.queue.priority

import androidx.annotation.CheckResult
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable

/** Queue for [Completable] scheduled by [Priority]. */
public interface RxPriorityQueue {

  /** Schedules [completable] with the given [priority]. */
  @CheckResult public fun schedule(priority: Priority, completable: Completable): Completable

  /** Factory for [RxPriorityQueue]. */
  public interface Factory {

    /**
     * Creates a new [RxPriorityQueue] which can execute at most [parallelism] tasks concurrently.
     */
    public fun create(parallelism: Int): RxPriorityQueue

    public companion object {

      /** Creates a new [RxPriorityQueue.Factory]. */
      public fun create(schedulerProvider: SchedulerProvider): Factory =
        RxPriorityQueueFactoryImpl(schedulerProvider)
    }
  }
}
