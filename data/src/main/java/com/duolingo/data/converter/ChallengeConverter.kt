package com.duolingo.data.converter

import com.duolingo.data.network.dto.ChallengeDTO
import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.ChallengeType
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

/**
 * A converter class used to convert [ChallengeDTO] to [Challenge] in the domain layer and
 * vice versa.
 */
class ChallengeConverter
@Inject constructor() {

    /**
     * Convert a [ChallengeDTO] into an [Challenge].
     * @param dto   Object to be transformed.
     * @return [Challenge] if [ChallengeDTO] is valid
     */
    fun convert(dto: ChallengeDTO): Challenge {
        return Challenge(
            id = LongId(dto.id),
            type = ChallengeType.FORWARD_TRANSLATION,
            question = dto.question,
            option1 = dto.option1,
            option2 = dto.option2,
            option3 = dto.option3,
            option4 = dto.option4,
            answer = dto.answer,
        )
    }
}