package com.duolingo.data.network.dto

import com.duolingo.domain.model.ChallengeType
import com.google.gson.annotations.SerializedName

data class ChallengeDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("type") val type: ChallengeType,
    @SerializedName("question") val question: String,
    @SerializedName("option1") val option1: String,
    @SerializedName("option2") val option2: String,
    @SerializedName("option3") val option3: String,
    @SerializedName("option4") val option4: String,
    @SerializedName("answer") val answer: String,
)
