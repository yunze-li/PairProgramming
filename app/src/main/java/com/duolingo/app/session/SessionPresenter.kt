package com.duolingo.app.session

import com.duolingo.app.base.Presenter
import com.duolingo.app.exception.ErrorMessageFactory
import com.duolingo.data.extensions.shareReplay
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Observable

class SessionPresenter
@AssistedInject constructor(
    @Assisted private val courseId: LongId<Course>,
    private val router: SessionRouter,
    private val courseRepository: CourseRepository,
    errorMessageFactory: ErrorMessageFactory
) : Presenter<SessionView, SessionData>(errorMessageFactory) {

    override fun attach(view: SessionView) {
        val loadSession = loadSession(courseId).shareReplay()
//        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
//        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }

        subscribeViewModel(view, loadSession)

//        loadRepo.filter { it.data != null }.map { it.data!! }
//            .switchMap { repo -> view.intentActionLink().map { repo.url } }
//            .subscribe { router.routeToLink(it) }
//            .addTo(composite)
    }

    private fun loadSession(courseId: LongId<Course>): Observable<SessionData> =
        courseRepository.observeCourse(courseId).toObservable()
            .map { SessionData.createData(it) }
            .onErrorReturn { onError(it) }
//        getRepo(getRepoParam)
//            .startWithSingle(RepoViewModel.createLoading())
//            .onErrorReturn { onError(it) }
//
//    private fun refreshData(getRepoParam: RefreshRepo.Param): Observable<RepoViewModel> =
//        refreshRepo.execute(getRepoParam).toObservable()
//            .map { RepoViewModel.createData(it) }
//            .onErrorReturn { RepoViewModel.createSnack(getErrorMessage(it)) }
//
//    private fun retryRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
//        getRepo(getRepoParam)
//            .startWithSingle(RepoViewModel.createRetryLoading())
//            .onErrorResumeNext(DelayFunction(scheduler))
//            .onErrorReturn { onError(it) }
//
//    private fun getRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
//        getRepo.execute(getRepoParam).toObservable()
//            .map { RepoViewModel.createData(it) }

    private fun onError(error: Throwable): SessionData =
        SessionData.createError(getErrorMessage(error))

    @AssistedFactory
    interface SessionPresenterFactory {
        fun create(courseId: LongId<Course>): SessionPresenter
    }

}
