package com.duolingo.domain.usecases

import com.duolingo.domain.exception.NoConnectedException
import com.duolingo.domain.exception.PersistenceException
import com.duolingo.domain.model.needCleanUp.Repo
import com.duolingo.domain.repository.NeedCleanUp.RepoRepository
import com.duolingo.domain.usecases.needCleanUp.GetRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetRepoTest {

    @Mock
    private lateinit var repository: RepoRepository

    // Properties
    private val repoName = "repoName"
    private val userName = "userName"
    private val repo = Repo(
        1,
        repoName,
        "description",
        "http://myrepo.com",
        false,
        userName
    )
    private val param = GetRepo.Param(repo.id, repo.name, userName)

    // Use Case
    private val useCase by lazy { GetRepo(repository) }

    @Test
    fun buildUseCase() {
        whenever(repository.isConnected).thenReturn(true)
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.just(repo))
        whenever(repository.saveRepo(repo)).thenReturn(Completable.complete())
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.just(repo))

        useCase.execute(param).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun buildUseCaseWithoutNetworkConnection() {
        whenever(repository.isConnected).thenReturn(false)
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.just(repo))
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.never())

        useCase.execute(param).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun buildUseCaseWithoutNetworkConnectionAndDataCache() {
        whenever(repository.isConnected).thenReturn(false)
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.empty()) // throw NoConnectedException here
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.never())

        useCase.execute(param).test()
            .assertError(NoConnectedException::class.java)
    }

    @Test
    fun buildUseCaseWithPersistenceException() {
        whenever(repository.isConnected).thenReturn(true)
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.just(repo))
        whenever(repository.saveRepo(repo)).thenReturn(Completable.error(PersistenceException))
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.empty())

        useCase.execute(param).test()
            .assertError(PersistenceException::class.java)
    }

}
