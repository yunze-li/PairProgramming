package com.duolingo.app.di.modules

import com.duolingo.app.mvvm.MvvmView
import com.duolingo.app.mvvm.MvvmViewDependencies
import dagger.Binds
import dagger.Module

/**
 * Dagger module that provides context.
 */
@Module
abstract class MvvmModule {

    @Binds
    abstract fun bindFactoryAsFactory(factory: MvvmViewDependencies.Factory):
            MvvmView.Dependencies.Factory

}
