package com.duolingo.rxjava.test.queue.priority

import com.duolingo.rxjava.queue.priority.RxPriorityQueue
import com.duolingo.rxjava.scheduler.SchedulerProvider
import com.duolingo.rxjava.test.scheduler.TestSchedulerProvider

/** Test-specific implementation of [RxPriorityQueue.Factory]. */
public class TestRxPriorityQueueFactory(
  schedulerProvider: SchedulerProvider = TestSchedulerProvider(),
) : RxPriorityQueue.Factory {

  private val delegate = RxPriorityQueue.Factory.create(schedulerProvider)

  override fun create(parallelism: Int): RxPriorityQueue = delegate.create(parallelism)
}
