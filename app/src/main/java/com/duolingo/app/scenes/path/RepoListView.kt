package com.duolingo.app.scenes.path

import com.duolingo.domain.model.needCleanUp.Repo
import com.duolingo.app.scenes.base.view.LoadDataView

import io.reactivex.rxjava3.core.Observable

interface RepoListView : LoadDataView<RepoListViewModel> {

    fun intentLoadData(): Observable<String>

    fun intentRefreshData(): Observable<String>

    fun intentErrorRetry(): Observable<String>

    fun intentFavorite(): Observable<Pair<Int, Repo>>

    fun openRepo(): Observable<Pair<Repo, String>>

}