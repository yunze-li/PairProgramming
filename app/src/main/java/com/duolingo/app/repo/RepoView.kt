package com.duolingo.app.repo

import com.duolingo.app.base.LoadDataView
import io.reactivex.rxjava3.core.Observable

interface RepoView : LoadDataView<RepoViewModel> {

    fun intentLoadData(): Observable<String>

    fun intentRefreshData(): Observable<String>

    fun intentErrorRetry(): Observable<String>

    fun intentActionLink(): Observable<Unit>

}