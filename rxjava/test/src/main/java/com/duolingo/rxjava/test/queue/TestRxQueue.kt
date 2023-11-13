package com.duolingo.rxjava.test.queue

import com.duolingo.rxjava.queue.RxQueue
import com.duolingo.rxjava.scheduler.SchedulerProvider
import com.duolingo.rxjava.test.scheduler.TestSchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/** Test-specific implementation of [RxQueue]. */
public class TestRxQueue(
  schedulerProvider: SchedulerProvider = TestSchedulerProvider(),
) : RxQueue {

  private val delegate = RxQueue.create(schedulerProvider)

  override fun deferredEnqueue(update: Completable): Completable = delegate.deferredEnqueue(update)

  override fun <T : Any> deferredEnqueue(update: Single<T>): Single<T> =
    delegate.deferredEnqueue(update)
}
