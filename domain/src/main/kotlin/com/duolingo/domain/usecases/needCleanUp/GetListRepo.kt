package com.duolingo.domain.usecases.needCleanUp

import com.duolingo.domain.exception.NoConnectedException
import com.duolingo.domain.utils.StatementSingle
import com.duolingo.domain.model.needCleanUp.Repo
import com.duolingo.domain.repository.NeedCleanUp.RepoRepository
import com.duolingo.domain.usecases.base.Logger
import com.duolingo.domain.usecases.base.SingleUseCase
import com.duolingo.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a collection of all [Repo].
 */
class GetListRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Repo>, String>(useCaseScheduler, logger) {

    override fun build(param: String): Single<List<Repo>> {
        val getCacheListRepo = repoRepository.getCacheListRepo(param)
            .flatMap { repoRepository.sortListRepo(it) }

        val cacheSingle = getCacheListRepo
            .map { it.ifEmpty { throw NoConnectedException } }

        val netSingle = repoRepository.getListRepo(param)
            .flatMap { repoRepository.saveListRepo(it).andThen(getCacheListRepo) }

        return StatementSingle.ifThen(repoRepository.isConnected, netSingle, cacheSingle)
    }

}
