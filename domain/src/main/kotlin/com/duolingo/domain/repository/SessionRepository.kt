package com.duolingo.domain.repository

import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/** An interface defined repository of course data. */
interface SessionRepository {

    val isConnected: Boolean

    /**
     * Get all sessions inside for a single course.
     */
    fun observeSessions(courseId: LongId<Course>): Flowable<List<Session>>

    /**
     * Get all challenges for a single session.
     */
    fun observeChallengesForSession(sessionId: LongId<Session>): Flowable<List<Challenge>>

}
