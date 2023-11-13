package com.duolingo.rxjava.test.scheduler

import com.duolingo.rxjava.scheduler.SchedulerFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/** Test-specific implementation of [SchedulerFactory]. */
public class TestSchedulerFactory(
  private val singleFactory: () -> Scheduler = { Schedulers.trampoline() },
) : SchedulerFactory {

  override fun single(): Scheduler = singleFactory.invoke()
}
