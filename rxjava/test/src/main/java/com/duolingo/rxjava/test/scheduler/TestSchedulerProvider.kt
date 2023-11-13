package com.duolingo.rxjava.test.scheduler

import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler

/** Test-specific implementation of [SchedulerProvider]. */
public class TestSchedulerProvider(
  override val main: Scheduler = Schedulers.trampoline(),
  override val computation: Scheduler = Schedulers.trampoline(),
  override val io: Scheduler = Schedulers.trampoline(),
  override val newThread: Scheduler = Schedulers.trampoline(),
) : SchedulerProvider {

  public companion object {

    private val UNSPECIFIED_SCHEDULER: Scheduler = TestScheduler()

    /**
     * Convenience creator of a [SchedulerProvider] which only supports the explicitly passed
     * [Scheduler]s. Attempting to use any other will result in an [AssertionError].
     *
     * Usage:
     * ```
     * val ioOnlyTrampoline = TestSchedulerProvider.only(io = Schedulers.trampoline())
     * val ioOnlyControlled = TestSchedulerProvider.only(io = TestScheduler(...))
     * ```
     */
    public fun only(
      main: Scheduler = UNSPECIFIED_SCHEDULER,
      computation: Scheduler = UNSPECIFIED_SCHEDULER,
      io: Scheduler = UNSPECIFIED_SCHEDULER,
      newThread: Scheduler = UNSPECIFIED_SCHEDULER,
      single: Scheduler = UNSPECIFIED_SCHEDULER,
    ): SchedulerProvider {

      fun specifiedOrThrow(scheduler: Scheduler, name: String): Scheduler =
        if (scheduler === UNSPECIFIED_SCHEDULER) {
          throw AssertionError("Provider's $name scheduler was used unexpectedly!")
        } else {
          scheduler
        }

      return object : SchedulerProvider {
        override val main: Scheduler by lazy { specifiedOrThrow(main, "main") }
        override val computation: Scheduler by lazy { specifiedOrThrow(computation, "computation") }
        override val io: Scheduler by lazy { specifiedOrThrow(io, "io") }
        override val newThread: Scheduler by lazy { specifiedOrThrow(newThread, "newThread") }
      }
    }
  }
}
