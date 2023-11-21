package com.duolingo.data.network.api

import com.duolingo.data.network.dto.ChallengeDTO
import com.duolingo.data.network.dto.CourseDTO
import com.duolingo.data.network.dto.SessionDTO

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DuoApi {

    @GET("duolingo-gateway/course/{courseId}")
    fun getCourse(
        @Path("courseId") courseId: Long,
    ): Single<CourseDTO>

    @GET("duolingo-gateway/courses/{userId}")
    fun getAllCourses(
        @Path("userId") userId: Long,
    ): Single<List<CourseDTO>>

    @GET("duolingo-gateway/session/{sessionId}")
    fun getSession(
        @Path("sessionId") sessionId: Long,
    ): Single<SessionDTO>

    @GET("duolingo-gateway/sessions/{courseId}")
    fun getAllSessions(
        @Path("courseId") courseId: Long,
    ): Single<List<SessionDTO>>

    @GET("duolingo-gateway/challenges/{sessionId}")
    fun getAllChallenges(
        @Path("sessionId") sessionId: Long,
    ): Single<List<ChallengeDTO>>
}
