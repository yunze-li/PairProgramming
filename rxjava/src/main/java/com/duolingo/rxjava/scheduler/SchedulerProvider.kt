package com.duolingo.rxjava.scheduler

import android.os.Looper
import io.reactivex.rxjava3.core.Scheduler

/** An injectable wrapper around [Scheduler] objects used by our reactive streams. */
public interface SchedulerProvider {

  /**
   * Shared [Scheduler] intended for main thread work.
   *
   * @see io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
   */
  public val main: Scheduler

  /**
   * Shared [Scheduler] intended for computation work.
   *
   * @see io.reactivex.rxjava3.schedulers.Schedulers.computation
   */
  public val computation: Scheduler

  /**
   * Shared [Scheduler] intended for IO-bound work.
   *
   * @see io.reactivex.rxjava3.schedulers.Schedulers.io
   */
  public val io: Scheduler

  /**
   * Shared [Scheduler] that creates a new thread for each unit of work.
   *
   * @see io.reactivex.rxjava3.schedulers.Schedulers.newThread
   */
  public val newThread: Scheduler

  public companion object {

    /** Creates a new [SchedulerProvider]. */
    public fun create(mainLooper: Looper): SchedulerProvider = SchedulerProviderImpl(mainLooper)
  }
}
