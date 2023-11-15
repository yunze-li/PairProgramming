package com.duolingo.app.courselist

import com.duolingo.app.base.LoadDataView
import com.duolingo.domain.model.Course

import io.reactivex.rxjava3.core.Observable

interface CourseListView : LoadDataView<CourseListData> {

    fun intentLoadData(): Observable<String>

    fun intentRefreshData(): Observable<String>

    fun intentErrorRetry(): Observable<String>

//    fun intentFavorite(): Observable<Pair<Int, Repo>>

    fun openCourse(): Observable<Pair<Course, String>>

}