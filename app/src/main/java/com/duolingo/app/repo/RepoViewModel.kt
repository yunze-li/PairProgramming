package com.duolingo.app.repo

import com.duolingo.app.base.ContentState
import com.duolingo.app.base.LoadingState
import com.duolingo.domain.needCleanUp.Repo

data class RepoViewModel(
    val loadingState: LoadingState = LoadingState.NONE,
    val contentState: ContentState = ContentState.NONE,
    val data: Repo? = null,
    val errorMessage: String? = null,
    val snackMessage: String? = null
) {

    companion object {
        fun createLoading() =
            RepoViewModel(loadingState = LoadingState.LOADING, contentState = ContentState.CONTENT)

        fun createRetryLoading() =
            RepoViewModel(loadingState = LoadingState.RETRY, contentState = ContentState.ERROR)

        fun createData(data: Repo) = RepoViewModel(contentState = ContentState.CONTENT, data = data)

        fun createError(error: String) =
            RepoViewModel(contentState = ContentState.ERROR, errorMessage = error)

        fun createSnack(snackMessage: String) =
            RepoViewModel(contentState = ContentState.CONTENT, snackMessage = snackMessage)
    }
}