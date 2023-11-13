package com.duolingo.rxjava.completable

import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/** Default implementation of our [CompletableFactory]. */
internal class CompletableFactoryImpl(
    private val schedulerProvider: SchedulerProvider,
) : CompletableFactory {

  override fun timer(
      delay: Long,
      unit: TimeUnit,
      scheduler: SchedulerProvider.() -> Scheduler,
  ): Completable =
    Completable./* Splinter ignore */ timer(delay, unit, schedulerProvider.scheduler())
}
