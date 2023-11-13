package com.duolingo.rxjava.processor

import androidx.annotation.CheckResult
import io.reactivex.rxjava3.core.Flowable

/** A wrapper around the more error-prone [io.reactivex.rxjava3.processors.FlowableProcessor]. */
public interface RxProcessor<T : Any> {

  /** Returns a stream of observable values of this processor. */
  @CheckResult
  public fun observe(
    backpressureStrategy: BackpressureStrategy = BackpressureStrategy.LATEST
  ): Flowable<T>

  /** Pushes an update to this variable. */
  public fun offer(next: T)

  /**
   * Factory for creating [RxProcessor] instances.
   *
   * @see io.reactivex.rxjava3.processors.BehaviorProcessor
   * @see io.reactivex.rxjava3.processors.PublishProcessor
   */
  public interface Factory {

    /**
     * Creates a new behavior [RxProcessor] instance.
     *
     * @see io.reactivex.rxjava3.processors.BehaviorProcessor.create
     */
    public fun <T : Any> behavior(): RxProcessor<T>

    /**
     * Creates a new behavior [RxProcessor] instance with a default.
     *
     * @param defaultValue The value that will be emitted first to any observer as long as no other
     * other values have been offered to the processor.
     *
     * @see io.reactivex.rxjava3.processors.BehaviorProcessor.createDefault
     */
    public fun <T : Any> behavior(defaultValue: T): RxProcessor<T>

    /**
     * Creates a new publish [RxProcessor] instance.
     *
     * @see io.reactivex.rxjava3.processors.PublishProcessor.create
     */
    public fun <T : Any> publish(): RxProcessor<T>

    public companion object {

      /** Creates a new [RxProcessor.Factory]. */
      public fun create(): Factory = RxProcessorFactoryImpl()
    }
  }
}
