package com.duolingo.app.di.modules

import android.os.Looper
import androidx.annotation.MainThread
import com.duolingo.rxjava.completable.CompletableFactory
import com.duolingo.rxjava.flowable.FlowableFactory
import com.duolingo.rxjava.processor.RxProcessor
import com.duolingo.rxjava.queue.RxQueue
import com.duolingo.rxjava.queue.priority.RxPriorityQueue
import com.duolingo.rxjava.scheduler.SchedulerFactory
import com.duolingo.rxjava.scheduler.SchedulerProvider
import com.duolingo.rxjava.variable.RxVariable
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Qualifier
import javax.inject.Singleton

/** Dependency module for our Rx implementations. */
@Module
object RxModule {

  @Provides
  fun provideCompletableFactory(schedulerProvider: SchedulerProvider): CompletableFactory =
    CompletableFactory.create(schedulerProvider)

  @Provides
  fun provideFlowableFactory(schedulerProvider: SchedulerProvider): FlowableFactory =
    FlowableFactory.create(schedulerProvider)

  @Provides
  fun provideRxProcessorFactory(): RxProcessor.Factory = RxProcessor.Factory.create()

  @Provides
  fun provideRxQueue(schedulerProvider: SchedulerProvider): RxQueue =
    RxQueue.create(schedulerProvider)

  @Provides
  fun provideRxPriorityQueueFactory(schedulerProvider: SchedulerProvider): RxPriorityQueue.Factory =
    RxPriorityQueue.Factory.create(schedulerProvider)

  @Provides
  fun provideRxVariableFactory(schedulerProvider: SchedulerProvider): RxVariable.Factory =
    RxVariable.Factory.create(schedulerProvider)

  @Provides fun provideSchedulerFactory(): SchedulerFactory = SchedulerFactory.create()

  @Provides
  fun provideSchedulerProvider(mainLooper: Looper): SchedulerProvider =
    SchedulerProvider.create(mainLooper)

  @Provides fun provideMainLooper(): Looper = Looper.getMainLooper()

  @Provides
  @PerformanceTrackingScheduler
  fun providePerformancesTrackingScheduler(): Scheduler =
    Schedulers.from(Executors.newSingleThreadExecutor())
}

/** Qualifier for scheduler used in performance tracking. */
@Qualifier annotation class PerformanceTrackingScheduler
