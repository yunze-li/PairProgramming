package com.duolingo.domain.utils

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleSource

/**
 * When an [Single] subscribes, the condition is evaluated
 * and the appropriate [SingleSource] is subscribed to.
 * @param <T> the common value type of the Single
 */
class SingleIfThen<T : Any>(
    private val condition: Boolean,
    private val then: SingleSource<out T>,
    private val orElse: SingleSource<out T>
) : Single<T>() {

    override fun subscribeActual(observer: SingleObserver<in T>) {
        if (condition) {
            then.subscribe(observer)
        } else {
            orElse.subscribe(observer)
        }
    }
}

