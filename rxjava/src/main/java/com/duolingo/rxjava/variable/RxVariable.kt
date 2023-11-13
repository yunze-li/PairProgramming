package com.duolingo.rxjava.variable

import androidx.annotation.CheckResult
import com.duolingo.rxjava.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/** A variable which can only be accessed reactively. */
public interface RxVariable<T : Any> {

  /** Returns a stream of observable values of this variable. */
  @CheckResult public fun observe(): Flowable<T>

  /** Pushes an update to this variable. */
  @CheckResult public fun update(update: (T) -> T): Completable

  /** Factory for creating [RxVariable] instances. */
  public interface Factory {

    /** Creates a new [RxVariable] with the given [initialValue]. */
    public fun <T : Any> create(initialValue: T): RxVariable<T>

    public companion object {

      /** Creates a new [RxVariable.Factory]. */
      public fun create(schedulerProvider: SchedulerProvider): Factory =
        RxVariableFactoryImpl(schedulerProvider)
    }
  }
}
