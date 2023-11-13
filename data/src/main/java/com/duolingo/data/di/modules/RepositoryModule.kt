package com.duolingo.data.di.modules

import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.extensions.api
import com.duolingo.data.mapper.RepoMapper
import com.duolingo.data.persistence.processor.RepoProcessor
import com.duolingo.data.repository.RepoDataRepository
import com.duolingo.data.repository.UserDataRepository
import com.duolingo.domain.repository.NeedCleanUp.RepoRepository
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

    // TODO: remove RepoDataRepository
    @Provides
    @Singleton
    internal fun provideRepoDataRepository(
        retrofit: Retrofit,
        repoMapper: RepoMapper,
        repoProcessor: RepoProcessor,
        networkChecker: NetworkChecker
    ): RepoRepository =
        RepoDataRepository(retrofit.api(), repoMapper, repoProcessor, networkChecker)

    @Provides
    @Singleton
    internal fun provideUserDataRepository(
        retrofit: Retrofit,
        repoMapper: RepoMapper,
        repoProcessor: RepoProcessor,
        networkChecker: NetworkChecker
    ): UserRepository =
        UserDataRepository(retrofit.api(), repoMapper, repoProcessor, networkChecker)

    @Provides
    @Singleton
    internal fun provideCourseDataRepository(
        retrofit: Retrofit,
        repoMapper: RepoMapper,
        repoProcessor: RepoProcessor,
        networkChecker: NetworkChecker
    ): UserRepository =
        UserDataRepository(retrofit.api(), repoMapper, repoProcessor, networkChecker)

}
