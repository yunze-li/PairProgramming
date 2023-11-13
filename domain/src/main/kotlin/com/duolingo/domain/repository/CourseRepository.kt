package com.duolingo.domain.repository

import com.duolingo.domain.model.Course
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/** An interface defined repository of course data. */
interface CourseRepository {

    /**
     * Get current course data by [userId].
     */
    fun observeCurrentCourse(userId: LongId<User>, courseId: LongId<Course>): Flowable<Course>

    /**
     * Get all courses supported.
     */
    fun observeAllAvailableCourses(): Flowable<List<Course>>

    /**
     * Refresh a course which matches the [courseId].
     */
    fun refreshCourse(courseId: LongId<Course>): Completable

    /**
     * Download a course which matches the [courseId].
     */
    fun downloadCourse(courseId: LongId<Course>): Completable

    /**
     * Invalidate a course which matches the [courseId].
     */
    fun invalidateCourse(courseId: LongId<Course>): Completable

}
