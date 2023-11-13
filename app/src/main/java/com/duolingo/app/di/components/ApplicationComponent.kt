package com.duolingo.app.di.components

import android.app.Application
import com.duolingo.data.di.components.DataComponent
import com.duolingo.app.di.PerApplication
import com.duolingo.app.di.modules.ApplicationModule
import com.duolingo.app.di.modules.UseCaseModule
import dagger.BindsInstance
import dagger.Component

/**
 * A component whose lifetime is the life of the application.
 */
@PerApplication // Constraints this component to one-per-application or unscoped bindings.
@Component(
    dependencies = [(DataComponent::class)],
    modules = [(ApplicationModule::class), (UseCaseModule::class)]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            dataComponent: DataComponent
        ): ApplicationComponent
    }

    fun activityComponent(): ActivityComponent.Factory

}
