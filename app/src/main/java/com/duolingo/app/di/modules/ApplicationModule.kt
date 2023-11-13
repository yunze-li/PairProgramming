package com.duolingo.app.di.modules

import android.app.Application
import android.content.Context
import com.duolingo.app.LoggerImpl
import com.duolingo.app.di.PerApplication
import com.duolingo.domain.base.Logger
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

    @Provides
    @PerApplication
    internal fun provideLogger(): Logger = LoggerImpl()

}
