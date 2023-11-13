package com.duolingo.rxjava.completable

import androidx.annotation.CheckResult
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/**
 * An injectable wrapper around error-prone [Completable] creation methods. In most cases, errors
 * arise from either automatic [Scheduler] selection within RxJava or lack of backpressure handling.
 */
public interface CompletableFactory {

  /** Wraps [Completable.timer], but specifies a more correct injectable [Scheduler]. */
  @CheckResult
  public fun timer(
    delay: Long,
    unit: TimeUnit,
    scheduler: SchedulerProvider.() -> Scheduler = { computation }
  ): Completable

  public companion object {

    /** Creates a new [CompletableFactory]. */
    public fun create(schedulerProvider: SchedulerProvider): CompletableFactory =
      CompletableFactoryImpl(schedulerProvider)
  }
}
