package com.duolingo.domain.model

import com.duolingo.domain.model.id.LongId

/** Data model of an user. */
data class User(
    val id: LongId<User>,
    val userName: String,
    val age: Int,
    val email: String,
    val streak: Streak,
    val isTrialUser: Boolean,
)
