package com.duolingo.app.scenes.path

import com.duolingo.data.extensions.startWithSingle
import com.duolingo.domain.utils.DelayFunction
import com.duolingo.domain.model.needCleanUp.Repo
import com.duolingo.domain.usecases.needCleanUp.FavoriteRepo
import com.duolingo.domain.usecases.needCleanUp.GetCacheRepo
import com.duolingo.domain.usecases.needCleanUp.GetListRepo
import com.duolingo.domain.usecases.needCleanUp.RefreshListRepo
import com.duolingo.app.exception.ErrorMessageFactory
import com.duolingo.app.scenes.base.presenter.APresenter
import com.duolingo.domain.usecases.base.Logger
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class RepoListPresenter
@Inject constructor(
    private val getListRepo: GetListRepo,
    private val refreshListRepo: RefreshListRepo,
    private val favoriteRepo: FavoriteRepo,
    private val getCacheRepo: GetCacheRepo,
    private val router: RepoListRouter,
    private val scheduler: Scheduler,
    private val logger: Logger,
    errorMessageFactory: ErrorMessageFactory
) : APresenter<RepoListView, RepoListViewModel>(errorMessageFactory) {

    override fun attach(view: RepoListView) {
        val loadRepo = view.intentLoadData().flatMap { loadRepo(it) }
        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }
        val favoriteRepo =
            view.intentFavorite().flatMap { (position, repo) -> favoriteRepo(position, repo) }

        subscribeViewModel(view, loadRepo, refreshRepo, retryRepo, favoriteRepo)

        view.openRepo()
            .subscribe { (repo, userName) -> router.routeToRepo(repo.id, repo.name, userName) }
            .addTo(composite)
    }

    //region USE CASES TO VIEW MODEL
    private fun loadRepo(userName: String): Observable<RepoListViewModel> =
        getListRepo.execute(userName).toObservable()
            .map { RepoListViewModel.createData(it) }
            .startWithSingle(RepoListViewModel.createLoading())
            .onErrorReturn { onError(it) }

    private fun refreshData(userName: String): Observable<RepoListViewModel> =
        refreshListRepo.execute(userName).toObservable()
            .map { RepoListViewModel.createData(it) }
            .onErrorReturn { RepoListViewModel.createSnack(getErrorMessage(it)) }

    private fun retryRepo(userName: String): Observable<RepoListViewModel> =
        getListRepo.execute(userName).toObservable()
            .map { RepoListViewModel.createData(it) }
            .startWithSingle(RepoListViewModel.createRetryLoading())
            .onErrorResumeNext(DelayFunction(scheduler))
            .onErrorReturn { onError(it) }

    private fun favoriteRepo(position: Int, repo: Repo): Observable<RepoListViewModel> =
        favoriteRepo.execute(repo).toSingleDefault(Unit).toObservable()
            .flatMap { getCacheRepo.execute(repo.id).toObservable() }
            .map { RepoListViewModel.createFavoriteRepo(position, it) }
            .onErrorReturn { onError(it) }
    //endregion

    private fun onError(error: Throwable): RepoListViewModel =
        RepoListViewModel.createError(getErrorMessage(error))

}
