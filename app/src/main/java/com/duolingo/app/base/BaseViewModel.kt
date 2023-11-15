package com.duolingo.app.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.flowables.ConnectableFlowable

/**
 * Abstract class representing a View Model in MVVM pattern.
 */
abstract class BaseViewModel: ViewModel() {

    private var hasConfigured: Boolean = false

    /**
     * Helper for running configuration code only once per [ViewModel] instance.
     *
     * To reiterate, [initializer] code runs only once per [ViewModel], not once per its host
     * [android.app.Activity] or [androidx.fragment.app.Fragment], so a configuration change in the
     * host will not result in a second run of this code. One edge case here is a forced Activity
     * recreation via [com.duolingo.core.util.Utils.restartActivity] which may completely recreate the
     * host Activity, resulting in a full recreation of its [ViewModel] as well.
     *
     * Usage:
     * ```
     * fun configure() {
     *   configureOnce {
     *     // Code in here runs only once for this ViewModel
     *   }
     *   // Code out here runs on every call to configure
     * }
     * ```
     */
    protected fun configureOnce(initializer: () -> Unit) {
        if (!hasConfigured) {
            initializer()
            hasConfigured = true
        }
    }


    /** Adds new disposable to unsubscribe from when the view model cleared. */
    @UiThread
    fun Disposable.unsubscribeOnCleared(): Unit = addCloseable { this.dispose() }

    /**
     * Converts this [Flowable] into a stream that is guaranteed to only emit the item at most once
     * per subscriber. Use this stream instead of something backed by a [PublishProcessor] for
     * communicating requests between the VM and the View that are only supposed to happen one time
     * (for example, a request to navigate to a different View).
     *
     * See documentation for more details.
     */
    protected fun <T : Any> Flowable<T>.asConsumable(): Flowable<T> {
        var capture: T? = null
        return this.doOnNext { capture = it }
            // Convert to an Observable before calling publish, if there are no subscribers then
            // `Flowable.publish` will buffer emissions while `Observable.publish` will drop them.
            // See: https://github.com/ReactiveX/RxJava/pull/6519
            .toObservable()
            .publish()
            .autoConnect(1) { it.unsubscribeOnCleared() }
            .toFlowable(BackpressureStrategy.MISSING)
            .concatWith(Flowable.never())
            .doOnCancel { capture = null }
            .startWith(Flowable.defer { Flowable.fromIterable(listOfNotNull(capture)) })
            .share()
    }

    /**
     * Variation on [ConsumableFlowable.autoConnect()] that is scoped to the lifecycle of this view
     * model. Using the parameterless [autoConnect] will subscribe to the upstream indefinitely. This
     * version will unsubscribe when the view model is cleared.
     */
    protected fun <T : Any> ConnectableFlowable<T>.autoConnectToViewModel(
        numberOfSubscribers: Int = 1
    ): Flowable<T> = autoConnect(numberOfSubscribers) { it.unsubscribeOnCleared() }

}
