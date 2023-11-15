package com.duolingo.data.di.modules

import com.duolingo.data.converter.CourseConverter
import com.duolingo.data.converter.UserConverter
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.persistence.processor.CourseProcessor
import com.duolingo.data.persistence.processor.UserProcessor
import com.duolingo.data.repository.CourseDataRepository
import com.duolingo.data.repository.SessionDataRepository
import com.duolingo.data.repository.UserDataRepository
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.domain.repository.SessionRepository
import com.duolingo.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger module that provides Repository class.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideUserDataRepository(
        retrofit: Retrofit,
        networkChecker: NetworkChecker,
        userConverter: UserConverter,
        userProcessor: UserProcessor,
    ): UserRepository =
        UserDataRepository(networkChecker, userConverter, userProcessor)

    @Provides
    @Singleton
    internal fun provideCourseDataRepository(
        retrofit: Retrofit,
        networkChecker: NetworkChecker,
        courseConverter: CourseConverter,
        courseProcessor: CourseProcessor,
    ): CourseRepository =
        CourseDataRepository(courseConverter, courseProcessor, networkChecker)

    @Provides
    @Singleton
    internal fun provideSessionDataRepository(
        networkChecker: NetworkChecker,
    ): SessionRepository =
        SessionDataRepository(networkChecker)

}
