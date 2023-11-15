package com.duolingo.domain.repository

import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/** An interface defined repository of course data. */
interface CourseRepository {

    val isConnected: Boolean

    /**
     * Refresh a course which matches the [courseId].
     */
    fun refreshCourse(courseId: LongId<Course>): Completable

    /**
     * Get current course data.
     */
    fun observeCourse(courseId: LongId<Course>): Single<Course>

    /**
     * Get all available courses for this user.
     */
    fun observeAllAvailableCourses(): Flowable<List<Course>>

    /**
     * Download a course which matches the [courseId].
     */
    fun downloadCourse(courseId: LongId<Course>): Completable

    /**
     * Invalidate a course which matches the [courseId].
     */
    fun invalidateCourse(courseId: LongId<Course>): Completable

}
