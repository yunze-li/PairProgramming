package com.duolingo.domain.model

import com.duolingo.domain.model.id.LongId

/** Data model of a session */
data class Session(
    val id: LongId<Session>,
//    val challengeIds: List<LongId<Challenge>>,
    val difficulty: Int,
    val displayName: String,
    val isCompleted: Boolean,
)
