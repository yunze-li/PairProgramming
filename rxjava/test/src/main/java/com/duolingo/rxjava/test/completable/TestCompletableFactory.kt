package com.duolingo.rxjava.test.completable

import com.duolingo.rxjava.completable.CompletableFactory
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/** Test-specific implementation of [CompletableFactory]. */
public class TestCompletableFactory : CompletableFactory {

  override fun timer(
    delay: Long,
    unit: TimeUnit,
    scheduler: SchedulerProvider.() -> Scheduler
  ): Completable = Completable.complete()
}
