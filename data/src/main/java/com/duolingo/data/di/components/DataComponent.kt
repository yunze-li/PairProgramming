package com.duolingo.data.di.components

import android.content.Context
import com.duolingo.data.di.modules.NetworkModule
import com.duolingo.data.di.modules.PersistenceModule
import com.duolingo.data.di.modules.RepositoryModule
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.domain.repository.SessionRepository
import com.duolingo.domain.repository.UserRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class), (PersistenceModule::class), (RepositoryModule::class)])
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    fun provideUserRepository(): UserRepository

    fun provideCourseRepository(): CourseRepository

    fun provideSessionRepository(): SessionRepository

}