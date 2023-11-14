package com.duolingo.app.repo

import com.duolingo.app.base.ContentState
import com.duolingo.app.base.LoadingState
import com.duolingo.domain.model.Course

data class SessionData(
    val loadingState: LoadingState = LoadingState.NONE,
    val contentState: ContentState = ContentState.NONE,
    val data: Course? = null,
    val errorMessage: String? = null,
    val snackMessage: String? = null
) {

    companion object {
        fun createLoading() =
            SessionData(loadingState = LoadingState.LOADING, contentState = ContentState.CONTENT)

        fun createRetryLoading() =
            SessionData(loadingState = LoadingState.RETRY, contentState = ContentState.ERROR)

        fun createData(data: Course) = SessionData(contentState = ContentState.CONTENT, data = data)

        fun createError(error: String) =
            SessionData(contentState = ContentState.ERROR, errorMessage = error)

        fun createSnack(snackMessage: String) =
            SessionData(contentState = ContentState.CONTENT, snackMessage = snackMessage)
    }
}