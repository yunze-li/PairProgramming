package com.duolingo.domain.repository

import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/** An interface defined repository of user data. */
interface UserRepository {

    val isConnected: Boolean

    /**
     * Refresh user data from remote.
     */
    fun refreshUser(userId: LongId<User>): Completable

    /**
     * Get cached user with [userId].
     */
    fun observeCachedUser(userId: LongId<User>): Single<User>

    /**
     * Get all users cached locally.
     */
    fun observeAllCachedUsers(): Single<List<User>>

    /**
     * Create a trial user from remote
     */
    fun createTrialUser(): Completable

}
