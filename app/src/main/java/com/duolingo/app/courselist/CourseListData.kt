package com.duolingo.app.courselist

import com.duolingo.app.base.ContentState
import com.duolingo.app.base.LoadingState
import com.duolingo.domain.model.Course

data class CourseListData(
    val loadingState: LoadingState = LoadingState.NONE,
    val contentState: ContentState = ContentState.NONE,
    val data: List<Course>? = null,
    val errorMessage: String? = null,
    val snackMessage: String? = null
) {
    companion object {
        fun createLoading() = CourseListData(
            loadingState = LoadingState.LOADING,
            contentState = ContentState.CONTENT
        )

        fun createRetryLoading() =
            CourseListData(loadingState = LoadingState.RETRY, contentState = ContentState.ERROR)

        fun createData(data: List<Course>) =
            CourseListData(contentState = ContentState.CONTENT, data = data)

        fun createError(error: String) =
            CourseListData(contentState = ContentState.ERROR, errorMessage = error)

        fun createSnack(snackMessage: String) =
            CourseListData(contentState = ContentState.CONTENT, snackMessage = snackMessage)
    }
}