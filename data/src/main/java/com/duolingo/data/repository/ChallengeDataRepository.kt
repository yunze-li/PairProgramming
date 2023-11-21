package com.duolingo.data.repository

import com.duolingo.data.converter.ChallengeConverter
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.network.api.DuoApi
import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.ChallengeRepository
import io.reactivex.rxjava3.core.Flowable

/**
 * [ChallengeRepository] for retrieving challenge data.
 */
class ChallengeDataRepository(
    private val challengeConverter: ChallengeConverter,
    private val duoApi: DuoApi,
    private val networkChecker: NetworkChecker
) : ChallengeRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    override fun fetchChallenges(sessionId: LongId<Session>): Flowable<List<Challenge>> {
        return duoApi.getAllChallenges(sessionId.get())
            .map {
                it.map { challengeDto -> challengeConverter.convert(challengeDto) }
            }.toFlowable()
    }

}
