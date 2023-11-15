package com.duolingo.app.challenge

import com.duolingo.app.base.BaseViewModel
import com.duolingo.app.session.SessionViewModel
import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.SessionRepository
import com.duolingo.rxjava.processor.RxProcessor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class ChallengeFragmentViewModel
@Inject constructor(
    rxProcessorFactory: RxProcessor.Factory,
) : BaseViewModel() {



//    @AssistedFactory
//    interface Factory {
//        fun create(challenge: Challenge): ChallengeFragmentViewModel
//    }
}