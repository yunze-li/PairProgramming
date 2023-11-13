package com.duolingo.rxjava.scheduler

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/** Default implementation of our [SchedulerProvider]. */
internal class SchedulerProviderImpl(mainLooper: Looper) : SchedulerProvider {

  override val main: Scheduler =
    InlineMainThreadScheduler(mainLooper, AndroidSchedulers./* Splinter ignore */ mainThread())

  override val computation: Scheduler = Schedulers./* Splinter ignore */ computation()

  override val io: Scheduler = Schedulers./* Splinter ignore */ io()

  override val newThread: Scheduler = Schedulers./* Splinter ignore */ newThread()
}
