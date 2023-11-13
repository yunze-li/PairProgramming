package com.duolingo.app.mvvm

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

/** Wrapper around the capabilities that any of our MVVM views can provide. */
interface MvvmView {

  /** Dependencies to be provided by any [MvvmView]. */
  val mvvmDependencies: Dependencies

  /**
   * Subscribes to the provided [flowable] while this [MvvmView]'s [androidx.lifecycle.Lifecycle] is
   * in the started state.
   *
   * This is intended to be called in `onCreate` for Activities and `onViewCreated` for Fragments in
   * order to set up ViewModel callbacks for UI updates.
   *
   * @param flowable Values stream to provide to the subscription callback.
   * @param subscriptionCallback The callback to run on new values.
   */
  fun <T : Any> whileStarted(
    flowable: Flowable<T>,
    subscriptionCallback: (T) -> Unit
  ): Unit =
    mvvmDependencies
      .uiLifecycleOwnerProvider
      .invoke()
      .lifecycle
      .addObserver(
        LifecycleAwareFlowableObserver(
          flowable = flowable,
          subscriptionCallback =
            mvvmDependencies.uiUpdatePerformanceWrapper.wrapWhileStartedCallback(
              subscriptionCallback,
              subscriptionCallback::class.java.toString()
            ),
          observeOnScheduler = mvvmDependencies.schedulerProvider.main,
        )
      )

  /**
   * Observes the provided [data] while this [MvvmView]'s [androidx.lifecycle.Lifecycle] is in the
   * started state.
   *
   * This is intended to be called in `onCreate` for Activities and `onViewCreated` for Fragments in
   * order to set up ViewModel callbacks for UI updates.
   *
   * @param data Values live data to provide to the observer.
   * @param observer The observer callback to run on new values
   */
  fun <T : Any?> observeWhileStarted(data: LiveData<T>, observer: Observer<in T>) {
    data.observe(
      mvvmDependencies.uiLifecycleOwnerProvider.invoke(),
      mvvmDependencies.uiUpdatePerformanceWrapper.wrapObserver(
        observer,
        observer::class.java.toString()
      )
    )
  }

  /** Wrapper for observable methods in [MvvmView]. */
  interface PerformanceWrapper {
    /** Wrap an [Observer] to track its performance, used for [MvvmView.observeWhileStarted]. */
    fun <T> wrapObserver(base: Observer<T>, name: String): Observer<T>

    /** Wrap a subscription callback to track its performance, used for [MvvmView.whileStarted]. */
    fun <T : Any> wrapWhileStartedCallback(base: (T) -> Unit, name: String): (T) -> Unit
  }

  /** Wrapper around the dependencies of an [MvvmView]. */
  interface Dependencies {
    val uiLifecycleOwnerProvider: () -> LifecycleOwner
    val schedulerProvider: SchedulerProvider
    val uiUpdatePerformanceWrapper: PerformanceWrapper

    /** Factory for [Dependencies]. */
    interface Factory {

      /** Creates a new instance. */
      fun create(
        uiLifecycleOwnerProvider: () -> LifecycleOwner,
      ): Dependencies
    }
  }

  /**
   * A [androidx.lifecycle.LifecycleObserver] which makes a given [flowable] and its
   * [subscriptionCallback] lifecycle-aware. Subscriptions to the [flowable] are only kept active
   * while this observer is in the started state. Emissions to [subscriptionCallback] are always
   * performed on the UI thread.
   */
  private class LifecycleAwareFlowableObserver<T : Any>(
    private val flowable: Flowable<T>,
    private val subscriptionCallback: (T) -> Unit,
    private val observeOnScheduler: Scheduler,
  ) : DefaultLifecycleObserver {

    private var subscription: Disposable? = null

    override fun onStart(owner: LifecycleOwner) {
      subscription =
        flowable.observeOn(observeOnScheduler).subscribe { subscriptionCallback.invoke(it) }
    }

    override fun onStop(owner: LifecycleOwner) {
      subscription?.dispose()
    }
  }
}
