package com.duolingo.app.path

import com.duolingo.app.base.LoadDataView
import com.duolingo.domain.needCleanUp.Repo

import io.reactivex.rxjava3.core.Observable

interface RepoListView : LoadDataView<RepoListViewModel> {

    fun intentLoadData(): Observable<String>

    fun intentRefreshData(): Observable<String>

    fun intentErrorRetry(): Observable<String>

    fun intentFavorite(): Observable<Pair<Int, Repo>>

    fun openRepo(): Observable<Pair<Repo, String>>

}