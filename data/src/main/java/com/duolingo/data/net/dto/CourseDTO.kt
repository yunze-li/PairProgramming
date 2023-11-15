package com.duolingo.data.net.dto

import android.se.omapi.Session
import com.google.gson.annotations.SerializedName

data class CourseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("ui_language_id") val uiLanguageId: String,
    @SerializedName("learning_language_id") val learningLanguageId: String,
    @SerializedName("sessions") val sessions: List<Session>? = null,
)
