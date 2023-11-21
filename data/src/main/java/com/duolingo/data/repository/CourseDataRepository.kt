package com.duolingo.data.repository

import android.util.Log
import com.duolingo.data.converter.CourseConverter
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.network.api.DuoApi
import com.duolingo.data.persistence.processor.CourseProcessor
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Language
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * [CourseRepository] for retrieving course data.
 */
class CourseDataRepository(
    private val courseConverter: CourseConverter,
    private val courseProcessor: CourseProcessor,
    private val duoApi: DuoApi,
    private val networkChecker: NetworkChecker
) : CourseRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    override fun fetchCourse(courseId: LongId<Course>): Flowable<Course> {
        return duoApi.getCourse(courseId.get())
            .map { courseConverter.convert(it) }
            .toFlowable()
    }

    override fun fetchAllCourses(userId: LongId<User>): Flowable<List<Course>> {
        return duoApi.getAllCourses(userId.get())
            .map {
                it.map { courseDto -> courseConverter.convert(courseDto) }
            }.toFlowable()
    }

    override fun downloadCourse(courseId: LongId<Course>): Completable {
//        Completable.fromAction { duoApi.downloadCourse(courseId.get()) }
        TODO("add download course")
    }

    override fun invalidateCourse(courseId: LongId<Course>): Completable {
//        Completable.fromAction { duoApi.invalidateCourse(courseId.get()) }
        TODO("add invalidate course")
    }

}
