package com.duolingo.domain.model

import com.duolingo.domain.model.id.LongId

/** Data model of a session */
data class Session(
    val id: LongId<Session>,
    val difficulty: Int,
    val challenges: List<Challenge>,
)
