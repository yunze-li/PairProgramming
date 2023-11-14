package com.duolingo.app.path

import com.duolingo.app.base.Presenter
import com.duolingo.app.exception.ErrorMessageFactory
import com.duolingo.domain.needCleanUp.Repo
import com.duolingo.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class RepoListPresenter
@Inject constructor(
    private val router: RepoListRouter,
    private val userRepository: UserRepository,
    errorMessageFactory: ErrorMessageFactory
) : Presenter<RepoListView, RepoListViewModel>(errorMessageFactory) {

    override fun attach(view: RepoListView) {
        val loadRepo = view.intentLoadData().flatMap { loadRepo() }
        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }
        val favoriteRepo =
            view.intentFavorite().flatMap { (position, repo) -> favoriteRepo(position, repo) }

        subscribeViewModel(view, loadRepo, refreshRepo, retryRepo, favoriteRepo)

        view.openRepo()
            .subscribe { (repo, userName) -> router.routeToRepo(repo.id, repo.name, userName) }
            .addTo(composite)
    }

    private fun loadRepo(): Observable<RepoListViewModel> =
        userRepository.observeMockRepo().toObservable()
            .map { RepoListViewModel.createData(it) }
            .onErrorReturn { onError(it) }

    private fun refreshData(userName: String): Observable<RepoListViewModel> {
        TODO("implement this")
    }

    private fun retryRepo(userName: String): Observable<RepoListViewModel> {
        TODO("implement this")
    }

    private fun favoriteRepo(position: Int, repo: Repo): Observable<RepoListViewModel> {
        TODO("implement this")
    }

    private fun onError(error: Throwable): RepoListViewModel =
        RepoListViewModel.createError(getErrorMessage(error))

}
