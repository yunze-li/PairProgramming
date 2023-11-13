package com.duolingo.rxjava.queue

import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.subjects.SingleSubject

/** Default implementation of our [RxQueue]. */
internal class RxQueueImpl(
    private val schedulerProvider: SchedulerProvider,
) : RxQueue {

  private val updates by lazy {
    PublishProcessor.create<Single<*>>().toSerialized().also {
      it.onBackpressureBuffer()
        .observeOn(schedulerProvider.computation)
        .concatMapSingle { it }
        .subscribe()
    }
  }

  override fun deferredEnqueue(update: Completable): Completable =
    deferredEnqueue(update.toSingleDefault(Unit)).ignoreElement()

  override fun <T : Any> deferredEnqueue(update: Single<T>): Single<T> =
    Single.defer {
      SingleSubject.create<T>().also { singleSubjectItem ->
        updates.onNext(
          update.doOnSuccess { singleSubjectItem.onSuccess(it) }.doOnError {
            singleSubjectItem.onError(it)
          }
        )
        // Workaround for RxJava memory leak in concatMapSingle
        // TODO @sarahbhaskaran report bug (CLARC-464)
        updates.onNext(Single.just(Unit))
      }
    }
}
