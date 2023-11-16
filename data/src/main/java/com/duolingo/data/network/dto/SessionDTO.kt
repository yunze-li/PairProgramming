package com.duolingo.data.network.dto

import com.google.gson.annotations.SerializedName

data class SessionDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("difficulty") val difficulty: Int,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("isCompleted") val isCompleted: Boolean,
)
