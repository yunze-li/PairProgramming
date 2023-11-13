package com.duolingo.domain.usecases

import com.duolingo.domain.exception.PersistenceException
import com.duolingo.domain.model.User
import com.duolingo.domain.repository.UserRepository
import com.duolingo.domain.usecases.base.FlowableUseCase
import com.duolingo.domain.usecases.base.Logger
import com.duolingo.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * This class is an implementation of [FlowableUseCase] that represents a use case for
 * retrieving a [User].
 */
class ObserveCurrentUser
@Inject internal constructor(
    private val userRepository: UserRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : FlowableUseCase<User, Nothing>(useCaseScheduler, logger) {

    override fun build(param: Nothing): Flowable<User> =
        userRepository.observeCurrentLoggedInUser()
            .switchIfEmpty(Flowable.error(PersistenceException))

}