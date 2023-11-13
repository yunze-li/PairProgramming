package com.duolingo.rxjava.processor

/** Our supported backpressure strategies of [RxProcessor]. */
public enum class BackpressureStrategy {

  /**
   * Buffers an unlimited number of items from the processor and allows it to emit as fast as it can
   * while allowing the downstream to consume the items at its own pace.
   *
   * @see [io.reactivex.rxjava3.core.Flowable.onBackpressureBuffer]
   */
  BUFFER,

  /**
   * Drops items from the processor if the downstream is not ready to receive new items.
   *
   * @see [io.reactivex.rxjava3.core.Flowable.onBackpressureDrop]
   */
  DROP,

  /**
   * Drops all but the latest item emitted by the processor if the downstream is not ready to
   * receive new items.
   *
   * @see [io.reactivex.rxjava3.core.Flowable.onBackpressureLatest]
   */
  LATEST,
}
