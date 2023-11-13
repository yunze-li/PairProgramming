package com.duolingo.domain.repository

import com.duolingo.domain.model.User
import com.duolingo.domain.model.needCleanUp.Repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/** An interface defined repository of user data. */
interface UserRepository {

    /**
     * Get current logged in user.
     */
    fun observeCurrentLoggedInUser(): Flowable<User>

    /**
     * Get all users stored locally.
     */
    fun observeAllUsers(): Flowable<List<User>>

    /**
     * Refresh user data stored locally.
     */
    fun refreshUser(user: User): Completable

    /**
     * Create a trial user
     */
    fun createTrialUser(): Completable

}
