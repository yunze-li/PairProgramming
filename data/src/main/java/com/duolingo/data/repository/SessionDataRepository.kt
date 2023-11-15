package com.duolingo.data.repository

import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.domain.base.Tuples
import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.SessionRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlin.coroutines.coroutineContext

/**
 * [RepoRepository] for retrieving course data.
 */
class SessionDataRepository(
    private val networkChecker: NetworkChecker
) : SessionRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    // TODO: add data layer in here
    private val courseToSessionMap: Map<Long, List<Session>> = mapOf(
        1L to listOf(
            Session(LongId(1L), listOf(), 1, "session 1", true),
            Session(LongId(2L), listOf(), 2, "session 2", false),
            Session(LongId(3L), listOf(), 3, "session 3", false),
        ),
        2L to listOf(
            Session(LongId(1L), listOf(), 1, "session 1", true),
            Session(LongId(2L), listOf(), 2, "session 2", false),
            Session(LongId(3L), listOf(), 3, "session 3", false),
        ),
        3L to listOf(
            Session(LongId(1L), listOf(), 1, "session 1", true),
            Session(LongId(2L), listOf(), 2, "session 2", false),
            Session(LongId(3L), listOf(), 3, "session 3", false),
        )
    )

    private val challenges = listOf(
        Challenge.ForwardTranslationChallenge(
            LongId(1L), Tuples.Tuple4("A", "B", "C", "D")
        )
    )

    override fun observeSessions(courseId: LongId<Course>): Flowable<List<Session>> {
        val sessions = courseToSessionMap[courseId.get()] ?: emptyList()
        return Flowable.just(sessions)
    }

    override fun observeChallengesForSession(sessionId: LongId<Session>): Flowable<List<Challenge>> {
        return Flowable.just(challenges)
    }

}
