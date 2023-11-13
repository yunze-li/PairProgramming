package com.duolingo.app.di.modules

import android.app.Application
import android.content.Context
import com.duolingo.app.di.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides context.
 */
@Module
class ApplicationModule {

    @Provides
    @PerApplication
    internal fun provideContext(application: Application): Context = application.baseContext

}
