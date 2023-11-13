package com.duolingo.domain.usecases.base

import com.duolingo.domain.usecases.base.Logger.Companion.INFO
import io.reactivex.rxjava3.core.Single

/**
 * A [UseCase] that will return a [Single] result.
 */
abstract class SingleUseCase<R : Any, in P>
constructor(
    private val useCaseScheduler: UseCaseScheduler?,
    private val logger: Logger?
) : UseCase<Single<R>, P>(logger) {

    override fun execute(param: P, fromUseCase: Boolean): Single<R> =
        super.execute(param, fromUseCase)
            .compose { transformer ->
                useCaseScheduler?.let {
                    if (fromUseCase) transformer
                    else transformer.subscribeOn(it.run).observeOn(it.post)
                } ?: transformer
            }
            .doOnError { logger?.logError("error in SingleUseCase", it) }
            .doOnSuccess { logger?.log(INFO, "${javaClass.simpleName} : $param => $it") }

}
