package com.duolingo.app.scenes.repo

import com.duolingo.domain.usecases.needCleanUp.GetRepo
import com.duolingo.domain.usecases.needCleanUp.RefreshRepo
import com.duolingo.app.scenes.base.view.LoadDataView
import io.reactivex.rxjava3.core.Observable

interface RepoView : LoadDataView<RepoViewModel> {

    fun intentLoadData(): Observable<GetRepo.Param>

    fun intentRefreshData(): Observable<RefreshRepo.Param>

    fun intentErrorRetry(): Observable<GetRepo.Param>

    fun intentActionLink(): Observable<Unit>

}