package com.duolingo.data.di.components

import android.content.Context
import com.duolingo.data.di.modules.NetModule
import com.duolingo.data.di.modules.PersistenceModule
import com.duolingo.data.di.modules.RepositoryModule
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.domain.repository.UserRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetModule::class), (PersistenceModule::class), (RepositoryModule::class)])
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    // Exposed to sub-graphs
    fun provideUserRepository(): UserRepository

    // Exposed to sub-graphs
    fun provideCourseRepository(): CourseRepository

}