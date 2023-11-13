package com.duolingo.rxjava.maybe

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

/** [Flowable.map] that emits only non-null [mapper] results. */
public fun <T : Any, R : Any> Maybe<T>.mapNotNull(mapper: (T) -> R?): Maybe<R> =
  this.flatMap { mapper(it)?.let { Maybe.just(it) } ?: Maybe.empty() }
