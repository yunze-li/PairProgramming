package com.duolingo.rxjava.flowable

import androidx.annotation.CheckResult
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/**
 * An injectable wrapper around error-prone [Flowable] creation methods. In most cases, errors arise
 * from either automatic [Scheduler] selection within RxJava or lack of backpressure handling.
 */
public interface FlowableFactory {

  /**
   * Wraps [Flowable.interval], but specifies a more correct injectable [Scheduler] and throttles
   * emissions.
   */
  @CheckResult
  public fun throttledInterval(
    period: Long,
    unit: TimeUnit,
    initialDelay: Long = period,
    scheduler: SchedulerProvider.() -> Scheduler = { computation }
  ): Flowable<Long>

  /** Wraps [Flowable.timer], but specifies a more correct injectable [Scheduler]. */
  @CheckResult
  public fun timer(
    delay: Long,
    unit: TimeUnit,
    scheduler: SchedulerProvider.() -> Scheduler = { computation }
  ): Flowable<Long>

  public companion object {

    /** Creates a new [FlowableFactory]. */
    public fun create(schedulerProvider: SchedulerProvider): FlowableFactory =
      FlowableFactoryImpl(schedulerProvider)
  }
}
