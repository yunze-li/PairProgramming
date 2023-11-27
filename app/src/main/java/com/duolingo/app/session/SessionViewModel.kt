package com.duolingo.app.session

import com.duolingo.app.base.BaseViewModel
import com.duolingo.app.challenge.ChallengeBridge
import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.ChallengeRepository
import com.duolingo.rxjava.processor.RxProcessor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Flowable

/** View model of [SessionActivity] */
class SessionViewModel
@AssistedInject constructor(
    @Assisted private val sessionId: LongId<Session>,
    private val challengeBridge: ChallengeBridge,
    rxProcessorFactory: RxProcessor.Factory,
    private val challengeRepository: ChallengeRepository,
    private val sessionRouteBridge: SessionRouteBridge,
) : BaseViewModel() {

    private lateinit var challengeIterator: Iterator<Challenge>

    val sessionRoute = Flowable.defer { sessionRouteBridge.route }.asConsumable()

    fun configure() = configureOnce {
        challengeRepository.fetchChallenges(sessionId)
            .firstElement()
            .subscribe { challenges ->
                challengeIterator = challenges.iterator()
                getNextChallenge()?.let {
                    sessionRouteBridge.navigate { showNextChallenge(it) }
                }
            }.unsubscribeOnCleared()

        challengeBridge.challengeCompleted.subscribe {
            val nextChallenge = getNextChallenge()
            if (nextChallenge != null) {
                sessionRouteBridge.navigate { showNextChallenge(nextChallenge) }
            } else {
                onChallengeFinished()
            }
        }.unsubscribeOnCleared()
    }

    private fun getNextChallenge(): Challenge? {
        return if (challengeIterator.hasNext()) {
            challengeIterator.next()
        } else {
            null
        }
    }

    private fun onChallengeFinished() {
        // TODO: add logic when challenge completed
    }

    @AssistedFactory
    interface Factory {
        fun create(sessionId: LongId<Session>): SessionViewModel
    }

}
