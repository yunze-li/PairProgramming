package com.duolingo.data.network.dto

import com.google.gson.annotations.SerializedName

data class CourseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("ui_language_id") val uiLanguageId: String,
    @SerializedName("learning_language_id") val learningLanguageId: String,
//    @SerializedName("sessionIds") val sessionIds: List<Long>,
)
