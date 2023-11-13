package com.duolingo.data.repository

import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.mapper.RepoMapper
import com.duolingo.data.net.api.DuoApi
import com.duolingo.data.net.dto.RepoDTO
import com.duolingo.data.persistence.entity.RepoEntity
import com.duolingo.data.persistence.processor.RepoProcessor
import com.duolingo.domain.model.needCleanUp.Repo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RepoDataRepositoryTest {

    @Mock
    private lateinit var api: DuoApi

    @Mock
    private lateinit var mapper: RepoMapper

    @Mock
    private lateinit var processor: RepoProcessor

    @Mock
    private lateinit var networkChecker: NetworkChecker

    private lateinit var repository: RepoDataRepository

    // Parameters
    private val repoId = 1L
    private val repoName = "repoName"
    private val userName = "userName"

    @Before
    fun setup() {
        repository = RepoDataRepository(api, mapper, processor, networkChecker)
    }

    //region LIST REPO
    @Test
    fun getListRepo() {
        val repoList = mock<List<Repo>>()
        val repoListDTO = mock<List<RepoDTO>>()

        whenever(api.getListRepos(userName)).thenReturn(Single.just(repoListDTO))
        whenever(mapper.transform(repoListDTO, userName)).thenReturn(repoList)

        repository.getListRepo(userName).test()
            .assertValueCount(1)
            .assertResult(repoList)
    }

    @Test
    fun getCacheListRepo() {
        val repoList = mock<List<Repo>>()
        val repoListEntities = mock<List<RepoEntity>>()

        whenever(processor.getAll(userName)).thenReturn(Single.just(repoListEntities))
        whenever(mapper.transform(repoListEntities)).thenReturn(repoList)

        repository.getCacheListRepo(userName).test()
            .assertValueCount(1)
            .assertResult(repoList)
    }

    @Test
    fun saveListRepo() {
        val repo = Repo(1, "repoName", "description", "http://myrepo.com", false, "userName")
        val repoEntity = RepoEntity(
            repo.id,
            repo.name,
            repo.description,
            repo.url,
            repo.isFavorite,
            repo.userName
        )
        val repoList = listOf(repo)

        whenever(processor.get(repo.id)).thenReturn(Maybe.just(repoEntity))
        whenever(mapper.transformToEntity(repo)).thenReturn(repoEntity)
        whenever(processor.insert(repoEntity)).thenReturn(Completable.complete())

        repository.saveListRepo(repoList).test()
            .assertResult()
    }
    //endregion

    //region REPO
    @Test
    fun getRepo() {
        val repo = mock<Repo>()
        val repoDTO = mock<RepoDTO>()

        whenever(api.getRepo(userName, repoName)).thenReturn(Single.just(repoDTO))
        whenever(mapper.transform(repoDTO, userName)).thenReturn(repo)

        repository.getRepo(repoName, userName).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun getCacheRepo() {
        val repo = mock<Repo>()
        val repoEntity = mock<RepoEntity>()

        whenever(processor.get(repoId)).thenReturn(Maybe.just(repoEntity))
        whenever(mapper.transform(repoEntity)).thenReturn(repo)

        repository.getCacheRepo(repoId).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun saveRepo() {
        val repo = Repo(1, "repoName", "description", "http://myrepo.com", false, "userName")
        val repoEntity = RepoEntity(
            repo.id,
            repo.name,
            repo.description,
            repo.url,
            repo.isFavorite,
            repo.userName
        )

        whenever(processor.get(repo.id)).thenReturn(Maybe.just(repoEntity))
        whenever(mapper.transformToEntity(repo)).thenReturn(repoEntity)
        whenever(processor.insert(repoEntity)).thenReturn(Completable.complete())

        repository.saveRepo(repo).test()
            .assertResult()
    }
    //endregion
}
