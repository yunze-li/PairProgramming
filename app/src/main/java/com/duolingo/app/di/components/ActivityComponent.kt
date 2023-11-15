package com.duolingo.app.di.components

import android.app.Activity
import com.duolingo.app.sessionlist.SessionListFragment
import com.duolingo.app.di.PerActivity
import com.duolingo.app.di.modules.ActivityModule
import com.duolingo.app.courselist.CourseListFragment
import com.duolingo.app.challenge.ChallengeFragment
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

    fun inject(fragment: SessionListFragment)

    fun inject(fragment: ChallengeFragment)

}
