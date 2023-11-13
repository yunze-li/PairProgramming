package com.duolingo.domain.utils

import com.duolingo.domain.exception.NoConnectedException

import io.reactivex.rxjava3.functions.Predicate

/**
 * [Predicate] that returns true if there's an available internet connection
 * or throw [NoConnectedException] if not.
 */
class ConnectionFilter : Predicate<Boolean> {

    @Throws(Exception::class)
    override fun test(isConnected: Boolean): Boolean {
        if (!isConnected) {
            throw NoConnectedException
        }
        return true
    }

}
