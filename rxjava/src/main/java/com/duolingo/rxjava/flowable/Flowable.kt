package com.duolingo.rxjava.flowable

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/** [Flowable.map] that emits only non-null [mapper] results. */
public fun <T : Any, R : Any> Flowable<T>.mapNotNull(mapper: (T) -> R?): Flowable<R> =
  this.concatMapMaybe { mapper(it)?.let { Maybe.just(it) } ?: Maybe.empty() }

/** [Single.map] that emits only non-null [mapper] results. */
public fun <T : Any, R : Any> Single<T>.mapNotNull(mapper: (T) -> R?): Maybe<R> =
  this.flatMapMaybe { mapper(it)?.let { Maybe.just(it) } ?: Maybe.empty() }

/**
 * A version of [Flowable.withLatestFrom()] that does not skip emissions from the current flowable,
 * or a version of [Flowable.zipWith] that does not queue emissions from the other flowable.
 *
 * This subscribes to the current flowable and the other flowable immediately, and emits once for
 * each emission of the current flowable. The emitted value is the result of running the combiner
 * function on the value from the current flowable and the most recent emission from the other
 * flowable.
 *
 * A note in the documentation for [Flowable.withLatestFrom()] describes the peculiarity that the
 * resulting stream will not emit unless all of its upstreams have already emitted by the tie that
 * the current stream emits. This is usually not what is desired, and this version instead queues
 * emissions from the current stream until all other streams have emitted, and then emits once per
 * emission from the current stream. This behavior is similar to how [Flowable.combineLatest()]
 * behaves.
 */
public fun <T : Any, U : Any, R : Any> Flowable<T>.zipWithLatestFrom(
  other: Flowable<U>,
  combiner: (T, U) -> R
): Flowable<R> =
  Flowable.defer {
    val replay = other.concatWith(Flowable.never()).replay(1).refCount()
    this.mergeWith(replay.ignoreElements()).concatMap {
      Flowable.just(it).zipWith(replay, combiner)
    }
  }
