package com.duolingo.core.rxjava.queue.priority

import com.duolingo.rxjava.queue.priority.Priority
import com.duolingo.rxjava.queue.priority.RxPriorityQueueFactoryImpl
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Test

class ProcessorRxPriorityQueueFactoryTest {

  private val testScheduler = TestScheduler()

  private val mockSchedulerProvider: SchedulerProvider = mockk {
    every { computation } returns testScheduler
  }

  private val factory =
    RxPriorityQueueFactoryImpl(
      schedulerProvider = mockSchedulerProvider,
    )

  @Test
  fun `when scheduling a task then the task is executed`() {
    val queue = factory.create(PARALLELISM)
    val action: () -> Unit = mockk(relaxed = true)
    val subscription = queue.schedule(Priority.LOW, Completable.fromAction(action)).test()
    testScheduler.triggerActions()
    verify(exactly = 1) { action.invoke() }
    subscription.assertComplete()
  }

  @Test
  fun `when scheduling tasks with different priorities then they are executed from highest to lowest priority`() {
    val queue = factory.create(PARALLELISM)
    val action1: () -> Unit = mockk(relaxed = true)
    val action2: () -> Unit = mockk(relaxed = true)
    val action3: () -> Unit = mockk(relaxed = true)
    val subscription1 = queue.schedule(Priority.LOW, Completable.fromAction(action1)).test()
    val subscription2 = queue.schedule(Priority.MEDIUM, Completable.fromAction(action2)).test()
    val subscription3 = queue.schedule(Priority.HIGH, Completable.fromAction(action3)).test()
    testScheduler.triggerActions()
    verify(ordering = Ordering.SEQUENCE) {
      action3.invoke()
      action2.invoke()
      action1.invoke()
    }
    subscription1.assertComplete()
    subscription2.assertComplete()
    subscription3.assertComplete()
  }

  companion object {
    private const val PARALLELISM = 1
  }
}
