package com.duolingo.core.rxjava.queue

import android.annotation.SuppressLint
import com.duolingo.rxjava.queue.RxQueueImpl
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.CompletableSubject
import io.reactivex.rxjava3.subjects.SingleSubject
import org.junit.Test

class RxQueueImplTest {

  private val mockSchedulerProvider: SchedulerProvider = mockk {
    every { computation } returns Schedulers.trampoline()
  }

  private val queue =
    RxQueueImpl(
      schedulerProvider = mockSchedulerProvider,
    )

  @Test
  @SuppressLint("CheckResult")
  fun `when deferred Completable update is enqueued then it is not processed until subscription`() {
    val updateAction: () -> Unit = mockk(relaxed = true)

    queue.deferredEnqueue(Completable.fromAction(updateAction))

    verify(exactly = 0) { updateAction.invoke() }
  }

  @Test
  @SuppressLint("CheckResult")
  fun `when deferred Single update is enqueued then it is not processed until subscription`() {
    val updateAction: () -> Unit = mockk(relaxed = true)

    queue.deferredEnqueue(Single.fromCallable(updateAction))

    verify(exactly = 0) { updateAction.invoke() }
  }

  @Test
  fun `when deferred Completable update is enqueued then it is processed upon subscription`() {
    val updateAction: () -> Unit = mockk(relaxed = true)

    queue.deferredEnqueue(Completable.fromAction(updateAction)).test().assertComplete()

    verify(exactly = 1) { updateAction.invoke() }
  }

  @Test
  fun `when deferred Single update is enqueued then it is processed upon subscription`() {
    val updateAction: () -> Unit = mockk(relaxed = true)

    queue.deferredEnqueue(Single.fromCallable { updateAction.invoke() }).test().assertComplete()

    verify(exactly = 1) { updateAction.invoke() }
  }

  @Test
  fun `when deferred Completable update is enqueued then return value completes when upstream completes`() {
    val subject = CompletableSubject.create()

    val subscriber = queue.deferredEnqueue(subject).test()

    subscriber.assertNotComplete()

    subject.onComplete()

    subscriber.assertComplete()
  }

  @Test
  fun `when deferred Single update is enqueued then return value completes when upstream completes`() {
    val subject = SingleSubject.create<Any>()

    val subscriber = queue.deferredEnqueue(subject).test()

    subscriber.assertNotComplete()

    subject.onSuccess(Unit)

    subscriber.assertComplete()
  }

  @Test
  fun `when multiple deferred updates are enqueued then they are processed in order of subscription`() {
    val updateAction1: () -> Unit = mockk(relaxed = true)
    val updateAction2: () -> Any = mockk(relaxed = true)
    val updateAction3: () -> Unit = mockk(relaxed = true)
    val updateAction4: () -> Any = mockk(relaxed = true)

    val update1 = queue.deferredEnqueue(Completable.fromAction(updateAction1))
    val update2 = queue.deferredEnqueue<Any>(Single.fromCallable(updateAction2))
    val update3 = queue.deferredEnqueue(Completable.fromAction(updateAction3))
    val update4 = queue.deferredEnqueue<Any>(Single.fromCallable(updateAction4))

    listOf(
        update2.test(),
        update4.test(),
        update1.test(),
        update3.test(),
      )
      .forEach { it.assertComplete() }

    verify(ordering = Ordering.ORDERED) {
      updateAction2.invoke()
      updateAction4.invoke()
      updateAction1.invoke()
      updateAction3.invoke()
    }
  }
}
