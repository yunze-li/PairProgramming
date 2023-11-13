package com.duolingo.app.exception

import android.content.Context
import com.duolingo.domain.exception.NoConnectedException
import com.duolingo.domain.exception.PersistenceException
import com.duolingo.app.R
import com.duolingo.domain.base.Logger
import javax.inject.Inject

/**
 * Factory used to create error messages from an Exception as a condition.
 */
open class ErrorMessageFactory
@Inject internal constructor(
    private val context: Context,
    private val logger: Logger,
    ) {

    /**
     * Creates a String representing an error message.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return [String] an error message.
     */
    open fun getError(exception: Throwable?): String =
        exception?.let {
            when (it) {
                is NoConnectedException -> context.getString(R.string.error_no_connection)
                is PersistenceException -> context.getString(R.string.error_persistence)
                else -> context.getString(R.string.error_generic)
            }.apply { logger.logError(this, it) }
        } ?: getGenericError()

    private fun getGenericError() = context.getString(R.string.error_generic)
}
