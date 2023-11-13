package com.duolingo.app.di.modules

import android.util.Log
import com.duolingo.app.LoggerImpl
import com.duolingo.domain.usecases.base.Logger
import com.duolingo.domain.usecases.base.UseCaseScheduler
import com.duolingo.app.di.PerApplication
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Dagger module that provides use cases from domain.
 */
@Module
class UseCaseModule {

    @Provides
    @PerApplication
    internal fun providePostScheduler() = AndroidSchedulers.mainThread()

    @Provides
    @PerApplication
    internal fun provideUseCaseScheduler(postScheduler: Scheduler) =
        UseCaseScheduler(Schedulers.io(), postScheduler)

    @Provides
    @PerApplication
    internal fun provideLogger(): Logger = LoggerImpl()
}
