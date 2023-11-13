package com.duolingo.domain.usecases.needCleanUp

import com.duolingo.domain.utils.ConnectionFilter
import com.duolingo.domain.model.needCleanUp.Repo
import com.duolingo.domain.repository.NeedCleanUp.RepoRepository
import com.duolingo.domain.usecases.base.Logger
import com.duolingo.domain.usecases.base.SingleUseCase
import com.duolingo.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a [Repo] but just with webservice (no cache data).
 */
class RefreshRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<Repo, RefreshRepo.Param>(useCaseScheduler, logger) {

    override fun build(param: Param): Single<Repo> =
        Single.just(repoRepository.isConnected)
            .filter(ConnectionFilter())
            .flatMapSingle {
                repoRepository.getRepo(param.name, param.userName)
                    .flatMap { repoRepository.saveRepo(it).andThen(Single.just(it)) }
            }.toSingle()

    data class Param(val id: Long, val name: String, val userName: String)

}
