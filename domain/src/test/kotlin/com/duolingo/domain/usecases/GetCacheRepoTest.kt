package com.duolingo.domain.usecases

import com.duolingo.domain.exception.PersistenceException
import com.duolingo.domain.model.needCleanUp.Repo
import com.duolingo.domain.usecases.needCleanUp.GetCacheRepo
import io.reactivex.rxjava3.core.Maybe
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetCacheRepoTest {

    @Mock
    private lateinit var repository: RepoRepository

    // Properties
    private val repoId: Long = 1
    private val repo =
        Repo(
            repoId,
            "repoName",
            "description",
            "http://myrepo.com",
            false,
            "userName"
        )

    // Use Case
    private val useCase by lazy { GetCacheRepo(repository) }

    @Test
    fun buildUseCase() {
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.just(repo))

        useCase.execute(repoId).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun buildUseCaseWithPersistenceException() {
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.empty())

        useCase.execute(repoId).test()
            .assertError(PersistenceException::class.java)
    }

}
