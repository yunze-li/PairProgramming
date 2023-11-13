package com.duolingo.rxjava.scheduler

import io.reactivex.rxjava3.core.Scheduler

/**
 * An injectable wrapper around the creation of [Scheduler] objects used by our reactive streams.
 */
public interface SchedulerFactory {

  /**
   * Creates a new single-threaded [Scheduler] which performs work sequentially on a single
   * background thread.
   */
  public fun single(): Scheduler

  public companion object {

    /** Creates a new [SchedulerFactory]. */
    public fun create(): SchedulerFactory = SchedulerFactoryImpl()
  }
}
