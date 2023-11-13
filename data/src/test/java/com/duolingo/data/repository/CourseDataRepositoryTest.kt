package com.duolingo.data.repository

import com.duolingo.data.converter.CourseConverter
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.net.api.DuoApi
import com.duolingo.data.persistence.processor.CourseProcessor
import io.mockk.mockk
import org.junit.Before

class CourseDataRepositoryTest {

    private val api: DuoApi = mockk()
    private val converter: CourseConverter = mockk()
    private val processor: CourseProcessor = mockk()
    private val networkChecker: NetworkChecker = mockk()

    private lateinit var repository: CourseDataRepository

    // Parameters
    private val repoId = 1L
    private val repoName = "repoName"

    @Before
    fun setup() {
        repository = CourseDataRepository(
            courseConverter = converter,
            courseProcessor = processor,
            duoApi = api,
            networkChecker = networkChecker,
            )
    }

//    @Test
//    fun getListRepo() {
//        val repoList = mock<List<Repo>>()
//        val repoListDTO = mock<List<RepoDTO>>()
//
//        whenever(api.getListRepos(userName)).thenReturn(Single.just(repoListDTO))
//        whenever(mapper.transform(repoListDTO, userName)).thenReturn(repoList)
//
//        repository.getListRepo(userName).test()
//            .assertValueCount(1)
//            .assertResult(repoList)
//    }
//
//    @Test
//    fun getCacheListRepo() {
//        val repoList = mock<List<Repo>>()
//        val repoListEntities = mock<List<RepoEntity>>()
//
//        whenever(processor.getAll(userName)).thenReturn(Single.just(repoListEntities))
//        whenever(mapper.transform(repoListEntities)).thenReturn(repoList)
//
//        repository.getCacheListRepo(userName).test()
//            .assertValueCount(1)
//            .assertResult(repoList)
//    }
//
//    @Test
//    fun saveListRepo() {
//        val repo = Repo(1, "repoName", "description", "http://myrepo.com", false, "userName")
//        val repoEntity = RepoEntity(
//            repo.id,
//            repo.name,
//            repo.description,
//            repo.url,
//            repo.isFavorite,
//            repo.userName
//        )
//        val repoList = listOf(repo)
//
//        whenever(processor.get(repo.id)).thenReturn(Maybe.just(repoEntity))
//        whenever(mapper.transformToEntity(repo)).thenReturn(repoEntity)
//        whenever(processor.insert(repoEntity)).thenReturn(Completable.complete())
//
//        repository.saveListRepo(repoList).test()
//            .assertResult()
//    }
//
//    @Test
//    fun getRepo() {
//        val repo = mock<Repo>()
//        val repoDTO = mock<RepoDTO>()
//
//        whenever(api.getRepo(userName, repoName)).thenReturn(Single.just(repoDTO))
//        whenever(mapper.transform(repoDTO, userName)).thenReturn(repo)
//
//        repository.getRepo(repoName, userName).test()
//            .assertValueCount(1)
//            .assertResult(repo)
//    }
//
//    @Test
//    fun getCacheRepo() {
//        val repo = mock<Repo>()
//        val repoEntity = mock<RepoEntity>()
//
//        whenever(processor.get(repoId)).thenReturn(Maybe.just(repoEntity))
//        whenever(mapper.transform(repoEntity)).thenReturn(repo)
//
//        repository.getCacheRepo(repoId).test()
//            .assertValueCount(1)
//            .assertResult(repo)
//    }
//
//    @Test
//    fun saveRepo() {
//        val repo = Repo(1, "repoName", "description", "http://myrepo.com", false, "userName")
//        val repoEntity = RepoEntity(
//            repo.id,
//            repo.name,
//            repo.description,
//            repo.url,
//            repo.isFavorite,
//            repo.userName
//        )
//
//        whenever(processor.get(repo.id)).thenReturn(Maybe.just(repoEntity))
//        whenever(mapper.transformToEntity(repo)).thenReturn(repoEntity)
//        whenever(processor.insert(repoEntity)).thenReturn(Completable.complete())
//
//        repository.saveRepo(repo).test()
//            .assertResult()
//    }
}
