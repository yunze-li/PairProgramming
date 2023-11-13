package com.duolingo.app.scenes.repo

import com.duolingo.domain.model.needCleanUp.Repo
import com.duolingo.app.scenes.base.view.ContentState
import com.duolingo.app.scenes.base.view.LoadingState

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