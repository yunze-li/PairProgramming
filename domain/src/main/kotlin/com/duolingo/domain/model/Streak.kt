package com.duolingo.domain.model

/** Data model of user's streak. */
data class Streak(
    val longestStreak: Int,
    val currentStreak: Int,
)
