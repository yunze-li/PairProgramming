package com.duolingo.domain.usecases.base

import io.reactivex.rxjava3.core.Completable

/**
 * A [UseCase] that will return a [Completable] result.
 */
abstract class CompletableUseCase<in P>
constructor(
    private val useCaseScheduler: UseCaseScheduler?,
    private val logger: Logger?
) : UseCase<Completable, P>(logger) {

    override fun execute(param: P, fromUseCase: Boolean): Completable =
        super.execute(param, fromUseCase)
            .compose { transformer ->
                useCaseScheduler?.let {
                    if (fromUseCase) transformer
                    else transformer.subscribeOn(it.run).observeOn(it.post)
                } ?: transformer
            }
            .doOnError { logger?.logError("error in CompletableUseCase", it) }
            .doOnComplete { logger?.log(Logger.INFO, "${javaClass.simpleName} : $param => completed") }

}
