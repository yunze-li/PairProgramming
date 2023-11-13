package com.duolingo.rxjava.single

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/** Kotlin convenience method for [Single.ofType]. */
public inline fun <reified R : Any> Single<*>.ofType(): Maybe<R> = ofType(R::class.java)
