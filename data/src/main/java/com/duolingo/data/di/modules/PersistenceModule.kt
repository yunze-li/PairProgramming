package com.duolingo.data.di.modules

import android.content.Context
import com.duolingo.data.persistence.AppDatabase
import com.duolingo.data.persistence.DatabaseFactory
import com.duolingo.data.persistence.dao.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides Persistence class.
 */
@Module
class PersistenceModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
        DatabaseFactory.getDatabase(context)

    @Provides
    @Singleton
    internal fun provideRepoDao(appDatabase: AppDatabase): RepoDao = appDatabase.repoDao()

}
