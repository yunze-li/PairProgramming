package com.duolingo.domain.usecases.base

import io.reactivex.rxjava3.core.Flowable


/**
 * A [UseCase] that will return a [Flowable] result.
 */
abstract class FlowableUseCase<T : Any, in P>
constructor(
    private val useCaseScheduler: UseCaseScheduler?,
    private val logger: Logger?
) : UseCase<Flowable<T>, P>(logger) {

    override fun execute(param: P, fromUseCase: Boolean): Flowable<T> =
        super.execute(param, fromUseCase)
            .compose { transformer ->
                useCaseScheduler?.let {
                    if (fromUseCase) transformer
                    else transformer.subscribeOn(it.run).observeOn(it.post)
                } ?: transformer
            }
            .doOnError { logger?.logError("error in CompletableUseCase", it) }
            .doOnNext { logger?.log(Logger.INFO, "${javaClass.simpleName} : $param => $it") }

}