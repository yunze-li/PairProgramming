package com.duolingo.rxjava.test.processor

import com.duolingo.rxjava.processor.RxProcessor

/** Test-specific implementation of [RxProcessor.Factory]. */
public class TestRxProcessorFactory : RxProcessor.Factory {

  private val delegate = RxProcessor.Factory.create()

  override fun <T : Any> behavior(): RxProcessor<T> = delegate.behavior()

  override fun <T : Any> behavior(defaultValue: T): RxProcessor<T> = delegate.behavior(defaultValue)

  override fun <T : Any> publish(): RxProcessor<T> = delegate.publish()
}
