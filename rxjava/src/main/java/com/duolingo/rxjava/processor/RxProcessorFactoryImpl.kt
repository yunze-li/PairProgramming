package com.duolingo.rxjava.processor

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.FlowableProcessor
import io.reactivex.rxjava3.processors.PublishProcessor

/** Default implementation of our [RxProcessor.Factory]. */
internal class RxProcessorFactoryImpl : RxProcessor.Factory {

  override fun <T : Any> behavior(): RxProcessor<T> =
    FlowableProcessorWrapper(BehaviorProcessor /* splinter ignore */.create<T>())

  override fun <T : Any> behavior(defaultValue: T): RxProcessor<T> =
    FlowableProcessorWrapper(BehaviorProcessor /* splinter ignore */.createDefault(defaultValue))

  override fun <T : Any> publish(): RxProcessor<T> =
    FlowableProcessorWrapper(PublishProcessor /* splinter ignore */.create<T>())

  /** [RxProcessor] backed by a [FlowableProcessor]. */
  private class FlowableProcessorWrapper<T : Any>(
    processor: FlowableProcessor<T>,
  ) : RxProcessor<T> {

    private val delegate = processor.toSerialized()

    override fun observe(backpressureStrategy: BackpressureStrategy): Flowable<T> =
      when (backpressureStrategy) {
        BackpressureStrategy.BUFFER -> delegate.onBackpressureBuffer()
        BackpressureStrategy.DROP -> delegate.onBackpressureDrop()
        BackpressureStrategy.LATEST -> delegate.onBackpressureLatest()
      }

    override fun offer(next: T) = delegate.onNext(next)
  }
}
