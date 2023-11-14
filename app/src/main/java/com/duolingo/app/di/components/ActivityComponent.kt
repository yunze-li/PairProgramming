package com.duolingo.app.di.components

import android.app.Activity
import com.duolingo.app.di.PerActivity
import com.duolingo.app.di.modules.ActivityModule
import com.duolingo.app.path.CourseListFragment
import com.duolingo.app.repo.SessionFragment
import dagger.BindsInstance
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): ActivityComponent
    }

    fun inject(fragment: CourseListFragment)

    fun inject(fragment: SessionFragment)

}
