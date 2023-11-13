package com.duolingo.app.mvvm

import androidx.lifecycle.LifecycleOwner
import com.duolingo.rxjava.scheduler.SchedulerProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import se.ansman.dagger.auto.AutoBind

/** Dagger injection wrapper for [MvvmView.Dependencies]. */
data class MvvmViewDependencies
@AssistedInject
constructor(
  @Assisted override val uiLifecycleOwnerProvider: () -> LifecycleOwner,
  override val schedulerProvider: SchedulerProvider,
  override val uiUpdatePerformanceWrapper: MvvmView.PerformanceWrapper,
) : MvvmView.Dependencies {

  /** Assisted DI factory for this dependency wrapper. */
  @AutoBind
  @AssistedFactory
  interface Factory : MvvmView.Dependencies.Factory {
    /** Creates a new instance of this dependencies wrapper. */
    override fun create(
      uiLifecycleOwnerProvider: () -> LifecycleOwner,
    ): MvvmViewDependencies
  }
}
