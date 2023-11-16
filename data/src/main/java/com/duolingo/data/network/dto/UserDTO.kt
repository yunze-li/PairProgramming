package com.duolingo.data.network.dto

import com.duolingo.domain.model.Streak
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId
import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id") val id: LongId<User>,
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("email") val email: String,
    @SerializedName("isTrialUser") val isTrialUser: Boolean,
    @SerializedName("currentCourseId") val currentCourseId: Long,
    @SerializedName("streak") val streak: Streak,
    @SerializedName("xp") val xp: Long,
)
