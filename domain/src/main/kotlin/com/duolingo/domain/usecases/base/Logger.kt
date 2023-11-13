package com.duolingo.domain.usecases.base

/** A general Logger interface */
interface Logger {

    /**
     * Logs a message.
     *
     * @param level Log message level.
     * @param message Message to log, if any.
     */
    fun log(level: Int, message: String)

    /**
     * Logs an error.
     *
     * @param message Message to log, if any.
     * @param throwable Throwable error to log, if any.
     */
    fun logError(message: String, throwable: Throwable)

    companion object {
        // Constants must be equivalent to the ones listed at
        // https://developer.android.com/reference/android/util/Log#constants_1
        const val VERBOSE = 2
        const val INFO = 4
        const val WARN = 5
        const val ERROR = 6
        const val ASSERT = 7
    }
}