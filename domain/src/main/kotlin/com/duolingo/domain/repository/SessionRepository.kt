package com.duolingo.domain.repository

import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/** An interface defined repository of course data. */
interface SessionRepository {

    val isConnected: Boolean

    /**
     * Fetch a session remotely which matches the [sessionId].
     */
    fun fetchSession(sessionId: LongId<Session>): Flowable<Session>

    /**
     * Fetch all sessions remotely for this course which matches the [courseId].
     */
    fun fetchAllSessions(courseId: LongId<Course>): Flowable<List<Session>>

}
