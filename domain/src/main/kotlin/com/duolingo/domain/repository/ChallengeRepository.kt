package com.duolingo.domain.repository

import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/** An interface defined repository of challenge data. */
interface ChallengeRepository {

    val isConnected: Boolean

    /**
     * Fetch all challenges remotely for this session which matches the [sessionId].
     */
    fun fetchChallenges(sessionId: LongId<Session>): Flowable<List<Challenge>>

}
