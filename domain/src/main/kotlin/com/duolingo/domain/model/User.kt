package com.duolingo.domain.model

import com.duolingo.domain.model.id.LongId

/** Data model of an user. */
data class User(
    val id: LongId<User>,
    val name: String,
    val age: Int,
    val email: String,
    val isTrialUser: Boolean,
    val currentCourseId: Long,
    val streak: Streak,
    val xp: Long,
)
