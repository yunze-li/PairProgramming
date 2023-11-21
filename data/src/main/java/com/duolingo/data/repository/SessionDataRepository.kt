package com.duolingo.data.repository

import com.duolingo.data.converter.SessionConverter
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.network.api.DuoApi
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
 * [SessionRepository] for retrieving course data.
 */
class SessionDataRepository(
    private val duoApi: DuoApi,
    private val networkChecker: NetworkChecker,
    private val sessionConverter: SessionConverter,
) : SessionRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

//    private val courseToSessionMap: Map<Long, List<Session>> = mapOf(
//        1L to listOf(
//            Session(LongId(1L), listOf(), 1, "session 1", true),
//            Session(LongId(2L), listOf(), 2, "session 2", false),
//            Session(LongId(3L), listOf(), 3, "session 3", false),
//        ),
//        2L to listOf(
//            Session(LongId(1L), listOf(), 1, "session 1", true),
//            Session(LongId(2L), listOf(), 2, "session 2", false),
//            Session(LongId(3L), listOf(), 3, "session 3", false),
//        ),
//        3L to listOf(
//            Session(LongId(1L), listOf(), 1, "session 1", true),
//            Session(LongId(2L), listOf(), 2, "session 2", false),
//            Session(LongId(3L), listOf(), 3, "session 3", false),
//        )
//    )

    override fun fetchSession(sessionId: LongId<Session>): Flowable<Session> {
        return duoApi.getSession(sessionId.get())
            .map { sessionConverter.convert(it) }
            .toFlowable()
    }

    override fun fetchAllSessions(courseId: LongId<Course>): Flowable<List<Session>> {
        return duoApi.getAllSessions(courseId.get())
            .map {
                it.map { sessionDto -> sessionConverter.convert(sessionDto) }
            }.toFlowable()
    }
}
