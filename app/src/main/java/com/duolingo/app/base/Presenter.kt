package com.duolingo.app.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.duolingo.app.exception.ErrorMessageFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Abstract class representing a Presenter in a model view presenter (MVI) pattern.
 */
abstract class Presenter<in View : LoadDataView<Data>, Data : Any>(private val errorMessageFactory: ErrorMessageFactory): ViewModel() {

    protected val composite = CompositeDisposable()

    @set:VisibleForTesting
    var testMode: Boolean = false

    abstract fun attach(view: View)

    protected fun subscribeViewModel(view: View, vararg observables: Observable<Data>) {
        if (!testMode) {
            composite.add(Observable.mergeArray(*observables).subscribe { view.render(it) })
        }
    }

    protected fun getErrorMessage(error: Throwable): String = errorMessageFactory.getError(error)

    fun detach() {
        composite.clear()
    }

}
