package com.duolingo.data.repository

import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.mapper.RepoMapper
import com.duolingo.data.net.api.DuoApi
import com.duolingo.data.persistence.processor.RepoProcessor
import com.duolingo.domain.model.User
import com.duolingo.domain.repository.NeedCleanUp.RepoRepository
import com.duolingo.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * [RepoRepository] for retrieving user data.
 */
class UserDataRepository(
    private val duoApi: DuoApi,
    private val repoMapper: RepoMapper,
    private val repoProcessor: RepoProcessor,
    private val networkChecker: NetworkChecker
) : UserRepository {

    override fun observeCurrentLoggedInUser(): Flowable<User> {
        TODO("Not yet implemented")
    }

    override fun observeAllUsers(): Flowable<List<User>> {
        TODO("Not yet implemented")
    }

    override fun refreshUser(user: User): Completable {
        TODO("Not yet implemented")
    }

    override fun createTrialUser(): Completable {
        TODO("Not yet implemented")
    }

}
