package com.duolingo.rxjava.flowable

import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/** Default implementation of our [FlowableFactory]. */
internal class FlowableFactoryImpl(
    private val schedulerProvider: SchedulerProvider,
) : FlowableFactory {

  override fun throttledInterval(
    period: Long,
    unit: TimeUnit,
    initialDelay: Long,
    scheduler: SchedulerProvider.() -> Scheduler
  ): Flowable<Long> =
    Flowable./* Splinter ignore */ interval(
        initialDelay,
        period,
        unit,
        schedulerProvider.scheduler(),
      )
      .onBackpressureLatest()

  override fun timer(
    delay: Long,
    unit: TimeUnit,
    scheduler: SchedulerProvider.() -> Scheduler
  ): Flowable<Long> =
    Flowable./* Splinter ignore */ timer(delay, unit, schedulerProvider.scheduler())
}
