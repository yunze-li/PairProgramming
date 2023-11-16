package com.duolingo.data.repository

import com.duolingo.data.converter.UserConverter
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.network.api.DuoApi
import com.duolingo.data.persistence.processor.UserProcessor
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * [RepoRepository] for retrieving user data.
 */
class UserDataRepository(
    private val duoApi: DuoApi,
    private val networkChecker: NetworkChecker,
    private val userConverter: UserConverter,
    private val userProcessor: UserProcessor,
) : UserRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    override fun refreshUser(userId: LongId<User>): Completable {
        TODO("add refresh user")
//        duoApi.getUser(userId.get())
//            .map { userConverter.convert(it) }
//            .map { userConverter.convertToEntity(it) }
//            .flatMapCompletable {
//                userProcessor.updateUser(it)
//            }
    }

    override fun observeCachedUser(userId: LongId<User>): Single<User> =
        userProcessor.get(userId.get())
            .map { userConverter.convert(it) }

    override fun observeAllCachedUsers(): Single<List<User>> =
        userProcessor.getAllUsers()
            .map { it.map { userEntity -> userConverter.convert(userEntity) } }

    override fun createTrialUser(): Completable {
        TODO("add create trial user")
//        duoApi.createTrialUser()
//            .map { userConverter.convert(it) }
//            .map { userConverter.convertToEntity(it) }
//            .flatMapCompletable {
//                userProcessor.updateUser(it)
//            }
    }
}
