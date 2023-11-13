package com.duolingo.domain.usecases.base

import io.reactivex.rxjava3.core.Maybe

/**
 * A [UseCase] that will return a [Maybe] result.
 */
abstract class MaybeUseCase<R, in P>
constructor(
    private val useCaseScheduler: UseCaseScheduler?,
    private val logger: Logger?
) : UseCase<Maybe<R>, P>(logger) {

    override fun execute(param: P, fromUseCase: Boolean): Maybe<R> =
        super.execute(param, fromUseCase)
            .compose { transformer ->
                useCaseScheduler?.let {
                    if (fromUseCase) transformer
                    else transformer.subscribeOn(it.run).observeOn(it.post)
                } ?: transformer
            }
            .doOnError { logger?.logError("error in MaybeUseCase", it) }
            .doOnSuccess { logger?.log(Logger.INFO, "${javaClass.simpleName} : $param => $it" ) }
}
