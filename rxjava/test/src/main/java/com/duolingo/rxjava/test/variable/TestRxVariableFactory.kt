package com.duolingo.rxjava.test.variable

import com.duolingo.rxjava.scheduler.SchedulerProvider
import com.duolingo.rxjava.test.scheduler.TestSchedulerProvider
import com.duolingo.rxjava.variable.RxVariable

/** Test-specific implementation of [RxVariable.Factory]. */
public class TestRxVariableFactory(
  schedulerProvider: SchedulerProvider = TestSchedulerProvider(),
) : RxVariable.Factory {

  private val delegate = RxVariable.Factory.create(schedulerProvider)

  override fun <T : Any> create(initialValue: T): RxVariable<T> = delegate.create(initialValue)
}
