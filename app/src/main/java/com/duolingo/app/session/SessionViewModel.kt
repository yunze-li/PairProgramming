package com.duolingo.app.session

import com.duolingo.app.base.BaseViewModel
import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.domain.repository.SessionRepository
import com.duolingo.rxjava.processor.RxProcessor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class SessionViewModel
@AssistedInject constructor(
    @Assisted private val sessionId: LongId<Session>,
    rxProcessorFactory: RxProcessor.Factory,
    private val sessionRepository: SessionRepository,
) : BaseViewModel() {

    private lateinit var challengeIterator: Iterator<Challenge>

    fun configure() = configureOnce {
        sessionRepository.observeChallengesForSession(sessionId)
            .firstElement()
            .subscribe { challenges ->
                challengeIterator = challenges.iterator()
                challengeProcessor.offer(challengeIterator.next())
            }.unsubscribeOnCleared()
    }

    private val challengeProcessor = rxProcessorFactory.behavior<Challenge>()
    val challenge: Flowable<Challenge> = challengeProcessor.observe().asConsumable()

    fun onChallengeFinished() {
        if (!challengeIterator.hasNext()) {
            challengeProcessor.offer(challengeIterator.next())
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(sessionId: LongId<Session>): SessionViewModel
    }

}
