package com.duolingo.rxjava.flowable

import io.reactivex.rxjava3.core.Flowable

/**
 * Returns a [Flowable] which produces only one emission when subscribed. While subscribed, it
 * captures the latest value of each flowable that is provided as an argument. The emitted value is
 * a function which will call [block] with the captured values when invoked. If a stream has not yet
 * emitted, the value for that stream will be null.
 *
 * This is useful for providing callbacks to views. Often, a callback (for example, an
 * [OnClickListener]) will need to use the value from some reactive stream in order to perform its
 * action. One way to accomplish this is to create a stream of [OnClickListener] s, and provide that
 * to the view as a LiveData. However, this will cause a new listener to be bound to the view for
 * every emission, which can be a lot of extra work when working with streams that emit frequently.
 * Instead, this method takes care of handling the lifecycle of subscribing to that stream so its
 * value can be read inline in the callback. Capturing values from streams this way incurs little
 * overhead. Each emission results only in a variable assignment.
 *
 * Example:
 * ```
 * val onSomeViewClick = captureLatest(stateManager) { state ->
 *     // Do something with [state]
 * }
 *
 * whileStarted(onSomeViewClick) { someView.setOnClickListener { it() } }
 * ```
 */
fun <F1 : Any, R> captureLatest(
  flowable1: Flowable<F1>,
  block: (F1?) -> R
): Flowable<() -> R> =
  Flowable.defer {
    var value1: F1? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
    ) { block(value1) }
  }

/** Variant of [captureLatest] that produces a callback that accepts a parameter. */
fun <F1 : Any, T, R> captureLatest(
  flowable1: Flowable<F1>,
  block: (T, F1?) -> R
): Flowable<(T) -> R> =
  Flowable.defer {
    var value1: F1? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
    ) { param -> block(param, value1) }
  }

/** Variant of [captureLatest] that captures two flowables. */
fun <F1 : Any, F2 : Any, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  block: (F1?, F2?) -> R
): Flowable<() -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
    ) { block(value1, value2) }
  }

/**
 * Variant of [captureLatest] that captures two flowables and produces a callback that accepts a
 * parameter.
 */
fun <F1 : Any, F2 : Any, T, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  block: (T, F1?, F2?) -> R
): Flowable<(T) -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
    ) { param -> block(param, value1, value2) }
  }

/** Variant of [captureLatest] that captures three flowables. */
fun <F1 : Any, F2 : Any, F3 : Any, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  block: (F1?, F2?, F3?) -> R
): Flowable<() -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
    ) { block(value1, value2, value3) }
  }

/**
 * Variant of [captureLatest] that captures three flowables and produces a callback that accepts a
 * parameter.
 */
fun <F1 : Any, F2 : Any, F3 : Any, T, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  block: (T, F1?, F2?, F3?) -> R
): Flowable<(T) -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
    ) { param -> block(param, value1, value2, value3) }
  }

/** Variant of [captureLatest] that captures four flowables. */
fun <F1 : Any, F2 : Any, F3 : Any, F4 : Any, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  flowable4: Flowable<F4>,
  block: (F1?, F2?, F3?, F4?) -> R
): Flowable<() -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    var value4: F4? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
      flowable4.doOnNext { value4 = it },
    ) { block(value1, value2, value3, value4) }
  }

/**
 * Variant of [captureLatest] that captures four flowables and produces a callback that accepts a
 * parameter.
 */
fun <F1 : Any, F2 : Any, F3 : Any, F4 : Any, T, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  flowable4: Flowable<F4>,
  block: (T, F1?, F2?, F3?, F4?) -> R
): Flowable<(T) -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    var value4: F4? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
      flowable4.doOnNext { value4 = it },
    ) { param -> block(param, value1, value2, value3, value4) }
  }

/** Variant of [captureLatest] that captures five flowables. */
fun <F1 : Any, F2 : Any, F3 : Any, F4 : Any, F5 : Any, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  flowable4: Flowable<F4>,
  flowable5: Flowable<F5>,
  block: (F1?, F2?, F3?, F4?, F5?) -> R
): Flowable<() -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    var value4: F4? = null
    var value5: F5? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
      flowable4.doOnNext { value4 = it },
      flowable5.doOnNext { value5 = it },
    ) { block(value1, value2, value3, value4, value5) }
  }

/**
 * Variant of [captureLatest] that captures five flowables and produces a callback that accepts a
 * parameter.
 */
fun <F1 : Any, F2 : Any, F3 : Any, F4 : Any, F5 : Any, T, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  flowable4: Flowable<F4>,
  flowable5: Flowable<F5>,
  block: (T, F1?, F2?, F3?, F4?, F5?) -> R
): Flowable<(T) -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    var value4: F4? = null
    var value5: F5? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
      flowable4.doOnNext { value4 = it },
      flowable5.doOnNext { value5 = it },
    ) { param -> block(param, value1, value2, value3, value4, value5) }
  }

/**
 * Variant of [captureLatest] that captures six flowables and produces a callback that accepts a
 * parameter.
 */
fun <F1 : Any, F2 : Any, F3 : Any, F4 : Any, F5 : Any, F6 : Any, T, R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  flowable4: Flowable<F4>,
  flowable5: Flowable<F5>,
  flowable6: Flowable<F6>,
  block: (T, F1?, F2?, F3?, F4?, F5?, F6?) -> R
): Flowable<(T) -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    var value4: F4? = null
    var value5: F5? = null
    var value6: F6? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
      flowable4.doOnNext { value4 = it },
      flowable5.doOnNext { value5 = it },
      flowable6.doOnNext { value6 = it },
    ) { param -> block(param, value1, value2, value3, value4, value5, value6) }
  }

/**
 * Variant of [captureLatest] that captures ten flowables and produces a callback that accepts a
 * parameter.
 */
fun <
  F1 : Any,
  F2 : Any,
  F3 : Any,
  F4 : Any,
  F5 : Any,
  F6 : Any,
  F7 : Any,
  F8 : Any,
  F9 : Any,
  T,
  R> captureLatest(
  flowable1: Flowable<F1>,
  flowable2: Flowable<F2>,
  flowable3: Flowable<F3>,
  flowable4: Flowable<F4>,
  flowable5: Flowable<F5>,
  flowable6: Flowable<F6>,
  flowable7: Flowable<F7>,
  flowable8: Flowable<F8>,
  flowable9: Flowable<F9>,
  block: (T, F1?, F2?, F3?, F4?, F5?, F6?, F7?, F8?, F9?) -> R
): Flowable<(T) -> R> =
  Flowable.defer {
    var value1: F1? = null
    var value2: F2? = null
    var value3: F3? = null
    var value4: F4? = null
    var value5: F5? = null
    var value6: F6? = null
    var value7: F7? = null
    var value8: F8? = null
    var value9: F9? = null
    captureLatestHelper(
      flowable1.doOnNext { value1 = it },
      flowable2.doOnNext { value2 = it },
      flowable3.doOnNext { value3 = it },
      flowable4.doOnNext { value4 = it },
      flowable5.doOnNext { value5 = it },
      flowable6.doOnNext { value6 = it },
      flowable7.doOnNext { value7 = it },
      flowable8.doOnNext { value8 = it },
      flowable9.doOnNext { value9 = it },
    ) { param ->
      block(param, value1, value2, value3, value4, value5, value6, value7, value8, value9)
    }
  }

private fun <T : Any> captureLatestHelper(vararg flowables: Flowable<*>, value: T) =
  Flowable.just(value).concatWith(Flowable.mergeArray(*flowables).ignoreElements())
