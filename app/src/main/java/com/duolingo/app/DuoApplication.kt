package com.duolingo.app

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.duolingo.data.di.components.DaggerDataComponent
import com.duolingo.data.di.components.DataComponent
import com.duolingo.app.di.components.ApplicationComponent
import com.duolingo.app.di.components.DaggerApplicationComponent
import timber.log.Timber

/**
 * The Main Application to start with.
 */
class DuoApplication : Application() {

    @set:VisibleForTesting
    lateinit var appComponent: ApplicationComponent

    @VisibleForTesting
    val dataComponent: DataComponent by lazy { DaggerDataComponent.factory().create(baseContext) }

    override fun onCreate() {
        super.onCreate()

        // Init Debug log
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Create App Component
        appComponent = createAppComponent()
    }

    @VisibleForTesting
    fun createAppComponent(): ApplicationComponent =
        DaggerApplicationComponent.factory()
            .create(this, dataComponent)

}
