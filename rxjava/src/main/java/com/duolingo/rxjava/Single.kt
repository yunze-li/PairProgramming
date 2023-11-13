package com.duolingo.rxjava

import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.disposables.Disposable
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Awaits a Single and returns its result.
 *
 * See
 * https://github.com/Kotlin/kotlinx.coroutines/blob/4116d4a178f32b1241db8247a38cd2823fb8b03e/reactive/kotlinx-coroutines-rx3/src/RxAwait.kt#L25
 */
public suspend fun <T : Any> SingleSource<T>.await(): T = suspendCancellableCoroutine { cont ->
  subscribe(
    object : SingleObserver<T> {
      override fun onSubscribe(d: Disposable) {
        cont.invokeOnCancellation { d.dispose() }
      }
      override fun onSuccess(t: T) {
        cont.resume(t)
      }
      override fun onError(error: Throwable) {
        cont.resumeWithException(error)
      }
    }
  )
}
