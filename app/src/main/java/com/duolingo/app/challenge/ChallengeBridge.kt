package com.duolingo.app.challenge

import com.duolingo.app.di.PerApplication
import com.duolingo.rxjava.processor.RxProcessor
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@PerApplication
class ChallengeBridge @Inject constructor(
    rxProcessorFactory: RxProcessor.Factory,
) {
    private val challengeCompletedProcessor = rxProcessorFactory.publish<Unit>()
    val challengeCompleted: Flowable<Unit> = challengeCompletedProcessor.observe().serialize()

    /** Navigate to specified route for activity */
    fun onChallengeCompleted() {
        challengeCompletedProcessor.offer(Unit)
    }
}