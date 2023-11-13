package com.duolingo.rxjava.queue

import androidx.annotation.CheckResult
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/** A queue for serializing [Single] updates. */
public interface RxQueue {

  /**
   * Creates a [Completable] which:
   * 1. Enqueues [update] when subscribed to
   * 2. Completes when [update] completes
   */
  @CheckResult public fun deferredEnqueue(update: Completable): Completable

  /**
   * Creates a [Single] which:
   * 1. Enqueues [update] when subscribed to
   * 2. Terminates when [update] terminates
   */
  @CheckResult public fun <T : Any> deferredEnqueue(update: Single<T>): Single<T>

  public companion object {

    /** Creates a new [RxQueue]. */
    public fun create(schedulerProvider: SchedulerProvider): RxQueue =
      RxQueueImpl(schedulerProvider)
  }
}
