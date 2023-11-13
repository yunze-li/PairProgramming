package com.duolingo.app.scenes.repo

import com.duolingo.data.extensions.shareReplay
import com.duolingo.data.extensions.startWithSingle
import com.duolingo.domain.utils.DelayFunction
import com.duolingo.domain.usecases.needCleanUp.GetRepo
import com.duolingo.domain.usecases.needCleanUp.RefreshRepo
import com.duolingo.app.exception.ErrorMessageFactory
import com.duolingo.app.scenes.base.presenter.APresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class RepoPresenter
@Inject constructor(
    private val getRepo: GetRepo,
    private val refreshRepo: RefreshRepo,
    private val router: RepoRouter,
    private val scheduler: Scheduler,
    errorMessageFactory: ErrorMessageFactory
) : APresenter<RepoView, RepoViewModel>(errorMessageFactory) {

    override fun attach(view: RepoView) {
        val loadRepo = view.intentLoadData().flatMap { loadRepo(it) }.shareReplay()
        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }

        subscribeViewModel(view, loadRepo, refreshRepo, retryRepo)

        loadRepo.filter { it.data != null }.map { it.data!! }
            .switchMap { repo -> view.intentActionLink().map { repo.url } }
            .subscribe { router.routeToLink(it) }
            .addTo(composite)
    }

    //region USE CASES TO VIEW MODEL
    private fun loadRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
        getRepo(getRepoParam)
            .startWithSingle(RepoViewModel.createLoading())
            .onErrorReturn { onError(it) }

    private fun refreshData(getRepoParam: RefreshRepo.Param): Observable<RepoViewModel> =
        refreshRepo.execute(getRepoParam).toObservable()
            .map { RepoViewModel.createData(it) }
            .onErrorReturn { RepoViewModel.createSnack(getErrorMessage(it)) }

    private fun retryRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
        getRepo(getRepoParam)
            .startWithSingle(RepoViewModel.createRetryLoading())
            .onErrorResumeNext(DelayFunction(scheduler))
            .onErrorReturn { onError(it) }

    private fun getRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
        getRepo.execute(getRepoParam).toObservable()
            .map { RepoViewModel.createData(it) }
    //endregion

    private fun onError(error: Throwable): RepoViewModel =
        RepoViewModel.createError(getErrorMessage(error))

}
