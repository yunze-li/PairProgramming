package com.duolingo.domain.model

import com.duolingo.domain.base.Tuples
import com.duolingo.domain.model.id.LongId
import java.io.Serializable

/** Data class for a challenge. */
sealed class Challenge(val id: LongId<Challenge>): Serializable {

    /**
     * A challenge type to give a word in ui language and ask user to select the correct translation
     * in learning language from 4 possible options (AKA "forward").
     */
    data class ForwardTranslationChallenge(
        val challengeId: LongId<Challenge>,
        val data: Tuples.Tuple4<String, String, String, String>,
    ) : Challenge(challengeId)

    /**
     * A challenge type to give a word in learning language and ask user to select the correct
     * translation in ui language from 4 possible options (AKA "backward").
     */
    data class BackwardTranslationChallenge(
        val challengeId: LongId<Challenge>,
        val data: Tuples.Tuple4<String, String, String, String>,
    ) : Challenge(challengeId)
}
