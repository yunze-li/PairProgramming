package com.duolingo.core.rxjava.variable

import com.duolingo.rxjava.scheduler.SchedulerProvider
import com.duolingo.rxjava.variable.RxVariableFactoryImpl
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Test

class RxVariableFactoryImplTest {

  private val mockSchedulerProvider: SchedulerProvider = mockk {
    every { computation } returns Schedulers.trampoline()
  }

  private val factory =
    RxVariableFactoryImpl(
      schedulerProvider = mockSchedulerProvider,
    )

  @Test
  fun `when no update is triggered then the initial value is observed`() {
    val variable = factory.create("a")

    variable.observe().test().assertValue("a")
  }

  @Test
  fun `when an update is triggered then it is observed`() {
    val variable = factory.create("a")
    val observed = variable.observe().test()

    variable.update { "${it}b" }

    observed.assertValues("a", "ab")
  }

  @Test
  fun `when updates are triggered inside an update then they are applied sequentially`() {
    val variable = factory.create("a")
    val observed = variable.observe().test()

    variable.update {
      variable.update { "${it}c" }
      variable.update { "${it}d" }
      "${it}b"
    }

    observed.assertValues("a", "ab", "abc", "abcd")
  }

  @Test
  fun `when updates are triggered inside a subscription then they are applied sequentially`() {
    val variable = factory.create("a")
    val observed = variable.observe().test()

    variable.observe().firstElement().subscribe {
      variable.update { "${it}b" }
      variable.update { "${it}c" }
    }

    observed.assertValues("a", "ab", "abc")
  }

  @Test
  fun `when an update is triggered then it completed only after it is observed`() {
    val variable = factory.create("a")

    Flowable.combineLatest(
        variable.observe(),
        Flowable.concat(
          Flowable.just("started"),
          Completable.defer { variable.update { "${it}b" } }.andThen(Flowable.just("completed")),
        ),
        ::Pair
      )
      .test()
      .assertValues(
        "a" to "started",
        "ab" to "started",
        "ab" to "completed",
      )
  }

  @Test
  fun `when an update is performed before a new subscription then the latest value is observed`() {
    val variable = factory.create("a")

    variable.update { "${it}b" }

    variable.observe().test().assertValue("ab")
  }
}
