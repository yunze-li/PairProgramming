package com.duolingo.data.network.dto

import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.id.LongId
import com.google.gson.annotations.SerializedName

data class SessionDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("difficulty") val difficulty: Int,
    @SerializedName("challenges") val challenges: List<LongId<Challenge>>,
)
