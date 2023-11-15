package com.duolingo.data.di.modules

import android.content.Context
import com.duolingo.data.persistence.AppDatabase
import com.duolingo.data.persistence.DatabaseFactory
import com.duolingo.data.persistence.dao.CourseDao
import com.duolingo.data.persistence.dao.SessionDao
import com.duolingo.data.persistence.dao.UserDao
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
        AppDatabase.getInstance(context)

    @Provides
    @Singleton
    internal fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    internal fun provideCourseDao(appDatabase: AppDatabase): CourseDao = appDatabase.courseDao()

    @Provides
    @Singleton
    internal fun provideSessionDao(appDatabase: AppDatabase): SessionDao = appDatabase.sessionDao()


}
