package com.duolingo.data.net.api

import com.duolingo.data.net.dto.CourseDTO
import com.duolingo.data.net.dto.UserDTO

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface DuoApi {

    @GET("duolingo-gateway/user/{userId}")
    fun getUser(
        @Path("userId") userId: Long,
    ): Single<UserDTO>

    @GET("duolingo-gateway/user/trial")
    fun createTrialUser(): Single<UserDTO>

    @GET("duolingo-gateway/course/{courseId}")
    fun getCourse(
        @Path("courseId") courseId: Long,
    ): Single<CourseDTO>

    @POST("duolingo-gateway/course/{courseId}/download")
    fun downloadCourse(
        @Path("courseId") courseId: Long,
    )

    @POST("duolingo-gateway/course/{courseId}/invalidate")
    fun invalidateCourse(
        @Path("courseId") courseId: Long,
    )

}
