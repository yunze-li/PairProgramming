package com.duolingo.app

import android.util.Log
import com.duolingo.domain.base.Logger

/** An implementation of [Logger] that uses [Log] to log messages. */
class LoggerImpl: Logger {

    override fun log(level: Int, message: String) {
        Log.println(level, TAG, message)
    }

    override fun logError(message: String, throwable: Throwable) {
        val trace = Log.getStackTraceString(throwable)
        Log.println(Log.ERROR, TAG, message.let { "$it\n$trace" })
    }

    companion object {
        private const val TAG = "DuoLog"
    }
}