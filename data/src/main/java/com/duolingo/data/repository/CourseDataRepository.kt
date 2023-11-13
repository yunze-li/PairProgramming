package com.duolingo.data.repository

import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.mapper.RepoMapper
import com.duolingo.data.net.api.DuoApi
import com.duolingo.data.persistence.processor.RepoProcessor
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.domain.repository.NeedCleanUp.RepoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * [RepoRepository] for retrieving user data.
 */
class CourseDataRepository(
    private val duoApi: DuoApi,
    private val repoMapper: RepoMapper,
    private val repoProcessor: RepoProcessor,
    private val networkChecker: NetworkChecker
) : CourseRepository {

    override fun observeCurrentCourse(
        userId: LongId<User>,
        courseId: LongId<Course>
    ): Flowable<Course> {
        TODO("Not yet implemented")
    }

    override fun observeAllAvailableCourses(): Flowable<List<Course>> {
        TODO("Not yet implemented")
    }

    override fun refreshCourse(courseId: LongId<Course>): Completable {
        TODO("Not yet implemented")
    }

    override fun downloadCourse(courseId: LongId<Course>): Completable {
        TODO("Not yet implemented")
    }

    override fun invalidateCourse(courseId: LongId<Course>): Completable {
        TODO("Not yet implemented")
    }

}
