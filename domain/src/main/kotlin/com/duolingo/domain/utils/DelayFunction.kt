package com.duolingo.domain.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import java.util.concurrent.TimeUnit

/**
 * 
 * [Function] that adds a delay to receiving the onComplete when a [Single] return an error
 */
class DelayFunction<T : Any>(private val postScheduler: Scheduler) :
    Function<Throwable, ObservableSource<out T>> {
    private val delay: Int = 1
    private val timeUnit: TimeUnit = TimeUnit.SECONDS

    @Throws(Exception::class)
    override fun apply(throwable: Throwable): ObservableSource<out T> =
        Observable.just(throwable)
            .delay(delay.toLong(), timeUnit, postScheduler)
            .flatMap { Observable.error(it) }

}
