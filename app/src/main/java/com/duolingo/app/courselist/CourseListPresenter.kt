package com.duolingo.app.courselist

import com.duolingo.app.base.Presenter
import com.duolingo.app.exception.ErrorMessageFactory
import com.duolingo.domain.repository.CourseRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class CourseListPresenter
@Inject constructor(
    private val router: CourseListRouter,
    private val courseRepository: CourseRepository,
    errorMessageFactory: ErrorMessageFactory
) : Presenter<CourseListView, CourseListData>(errorMessageFactory) {

    override fun attach(view: CourseListView) {
        val loadRepo = view.intentLoadData().flatMap { loadRepo() }
        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }

        subscribeViewModel(view, loadRepo, refreshRepo, retryRepo)

        view.openCourse()
            .subscribe { (course, userName) -> router.routeToSession(course.id) }
            .addTo(composite)
    }

    private fun loadRepo(): Observable<CourseListData> =
        courseRepository.observeAllAvailableCourses().toObservable()
            .map { CourseListData.createData(it) }
            .onErrorReturn { onError(it) }

    private fun refreshData(userName: String): Observable<CourseListData> {
        TODO("implement this")
    }

    private fun retryRepo(userName: String): Observable<CourseListData> {
        TODO("implement this")
    }

    private fun onError(error: Throwable): CourseListData =
        CourseListData.createError(getErrorMessage(error))

}
