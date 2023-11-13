package com.duolingo.rxjava.scheduler

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/** Default implementation of our [SchedulerFactory]. */
internal class SchedulerFactoryImpl : SchedulerFactory {

  override fun single(): Scheduler {
    val atMostSinglyThreadedPoolExecutor =
      ThreadPoolExecutor(
        /* corePoolSize = */ 0,
        /* maximumPoolSize = */ 1,
        /* keepAliveTime = */ 30L,
        /* unit = */ TimeUnit.SECONDS,
        /* workQueue = */ LinkedBlockingQueue(),
      )
    return Schedulers.from(atMostSinglyThreadedPoolExecutor)
  }
}
