package com.duolingo.rxjava.test.flowable

import com.duolingo.rxjava.flowable.FlowableFactory
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/** Test-specific implementation of [FlowableFactory]. */
public class TestFlowableFactory(
  private val intervalCount: Long = 1L,
) : FlowableFactory {

  override fun throttledInterval(
    period: Long,
    unit: TimeUnit,
    initialDelay: Long,
    scheduler: SchedulerProvider.() -> Scheduler,
  ): Flowable<Long> = Flowable.fromIterable(0L until intervalCount)

  override fun timer(
    delay: Long,
    unit: TimeUnit,
    scheduler: SchedulerProvider.() -> Scheduler
  ): Flowable<Long> = Flowable.just(0L)
}
