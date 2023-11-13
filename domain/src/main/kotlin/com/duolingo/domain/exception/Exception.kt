package com.duolingo.domain.exception

sealed class AppException(message: String) : RuntimeException(message)

/** An exception for when there is no logged in user. */
object UserLoggedOutException : AppException("User is logged out.")


/** An exception for when there is no logged in user. */
object NoSuchCourseException : AppException("No such a course supported by the app.")

/**
 * Exception used when it is impossible to get data due to a lack of connection
 */
object NoConnectedException : AppException("No connection")

/**
 * Exception used when persistence method return false on SingleUseCase
 */
object PersistenceException : AppException("Data persistence error occurred")