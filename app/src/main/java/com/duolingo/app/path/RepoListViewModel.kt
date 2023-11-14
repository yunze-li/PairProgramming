package com.duolingo.app.path

import com.duolingo.app.base.ContentState
import com.duolingo.app.base.LoadingState
import com.duolingo.domain.needCleanUp.Repo

data class RepoListViewModel(
    val loadingState: LoadingState = LoadingState.NONE,
    val contentState: ContentState = ContentState.NONE,
    val data: List<Repo>? = null,
    val favoriteRepo: Repo? = null,
    val favoriteRepoPosition: Int? = null,
    val errorMessage: String? = null,
    val snackMessage: String? = null
) {
    companion object {
        fun createLoading() = RepoListViewModel(
            loadingState = LoadingState.LOADING,
            contentState = ContentState.CONTENT
        )

        fun createRetryLoading() =
            RepoListViewModel(loadingState = LoadingState.RETRY, contentState = ContentState.ERROR)

        fun createData(data: List<Repo>) =
            RepoListViewModel(contentState = ContentState.CONTENT, data = data)

        fun createFavoriteRepo(position: Int, repo: Repo) =
            RepoListViewModel(
                contentState = ContentState.CONTENT,
                favoriteRepo = repo,
                favoriteRepoPosition = position
            )

        fun createError(error: String) =
            RepoListViewModel(contentState = ContentState.ERROR, errorMessage = error)

        fun createSnack(snackMessage: String) =
            RepoListViewModel(contentState = ContentState.CONTENT, snackMessage = snackMessage)
    }
}