package com.duolingo.domain.model

import com.duolingo.domain.model.id.LongId
import java.io.Serializable

/** Data class for a challenge. */
data class Challenge(
    val id: LongId<Challenge>,
    val type: ChallengeType,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val answer: String,
): Serializable

enum class ChallengeType {

    /**
     * A challenge type to give a word in ui language and ask user to select the correct translation
     * in learning language from 4 possible options (AKA "forward").
     */
    FORWARD_TRANSLATION,

    /**
     * A challenge type to give a word in learning language and ask user to select the correct
     * translation in ui language from 4 possible options (AKA "backward").
     */
    BACKWARD_TRANSLATION,
}
