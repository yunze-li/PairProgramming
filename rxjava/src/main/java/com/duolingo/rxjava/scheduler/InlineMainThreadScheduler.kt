package com.duolingo.rxjava.scheduler

import android.os.Looper
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Shared [Scheduler] corresponding to Android's main thread. Unlike the one provided by RxJava,
 * this explicitly inlines any work when already running on Android's main thread.
 */
internal class InlineMainThreadScheduler(
  private val mainLooper: Looper,
  private val mainThreadScheduler: Scheduler,
) : Scheduler() {

  override fun createWorker(): Worker =
    WorkerImpl(
      delegate = mainThreadScheduler.createWorker(),
      mainLooper = mainLooper,
    )

  private class WorkerImpl(
    val delegate: Worker,
    val mainLooper: Looper,
  ) : Worker() {

    override fun schedule(run: Runnable): Disposable {
      if (isDisposed) {
        return Disposable.disposed()
      }
      if (mainLooper === Looper.myLooper()) {
        run.run()
        return Disposable.disposed()
      }
      return delegate.schedule(run)
    }

    override fun schedule(run: Runnable, delay: Long, unit: TimeUnit): Disposable =
      delegate.schedule(run, delay, unit)

    override fun dispose() = delegate.dispose()

    override fun isDisposed() = delegate.isDisposed
  }
}
