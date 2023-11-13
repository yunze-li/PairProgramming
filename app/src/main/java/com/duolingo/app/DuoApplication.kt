package com.duolingo.app

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.duolingo.app.di.components.ApplicationComponent
import com.duolingo.app.di.components.DaggerApplicationComponent
import com.duolingo.data.di.components.DaggerDataComponent
import com.duolingo.data.di.components.DataComponent


class DuoApplication : Application() {

    @set:VisibleForTesting
    lateinit var appComponent: ApplicationComponent

    @VisibleForTesting
    val dataComponent: DataComponent by lazy { DaggerDataComponent.factory().create(baseContext) }

    override fun onCreate() {
        super.onCreate()

        // Create App Component
        appComponent = createAppComponent()
    }

    @VisibleForTesting
    fun createAppComponent(): ApplicationComponent =
        DaggerApplicationComponent.factory()
            .create(this, dataComponent)

}
