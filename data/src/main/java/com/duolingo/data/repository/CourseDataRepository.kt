package com.duolingo.data.repository

import com.duolingo.data.converter.CourseConverter
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.net.api.DuoApi
import com.duolingo.data.persistence.processor.CourseProcessor
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * [RepoRepository] for retrieving course data.
 */
class CourseDataRepository(
    private val courseConverter: CourseConverter,
    private val courseProcessor: CourseProcessor,
//    private val duoApi: DuoApi,
    private val networkChecker: NetworkChecker
) : CourseRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    override fun refreshCourse(courseId: LongId<Course>): Completable {
        TODO("add refresh course")
//        duoApi.getCourse(courseId.get())
//            .map { courseConverter.convert(it) }
//            .map { courseConverter.convertToEntity(it) }
//            .flatMapCompletable {
//                courseProcessor.updateCourse(it)
//            }
    }

    override fun observeCachedCourse(
        courseId: LongId<Course>,
    ): Single<Course> =
        courseProcessor.get(courseId.get())
            .map { courseConverter.convert(it) }

    override fun observeAllCachedCourses(): Single<List<Course>> =
        courseProcessor.getAllCourses()
            .map { it.map { courseEntity -> courseConverter.convert(courseEntity) } }

    override fun downloadCourse(courseId: LongId<Course>): Completable {
//        Completable.fromAction { duoApi.downloadCourse(courseId.get()) }
        TODO("add download course")
    }

    override fun invalidateCourse(courseId: LongId<Course>): Completable {
//        Completable.fromAction { duoApi.invalidateCourse(courseId.get()) }
        TODO("add invalidate course")
    }

}
