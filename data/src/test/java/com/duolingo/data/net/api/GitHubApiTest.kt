package com.duolingo.data.net.api

import android.content.Context
import com.google.gson.Gson
import com.duolingo.data.net.OkHttpClientFactoryTest
import com.duolingo.data.net.RetrofitFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class GitHubApiTest {

    private val context = mock<Context>()
    private val retrofit = RetrofitFactory.getRetrofit(context, Gson(), OkHttpClientFactoryTest())

    private val service: DuoApi
        get() = retrofit.create(DuoApi::class.java)

    @Test
    fun getListReposWebservice() {
        service.getListRepos("lopspower").test()
            .assertNoErrors()
    }

    @Test
    fun getRepoWebservice() {
        service.getRepo("lopspower", "CircularImageView").test()
            .assertNoErrors()
    }
}
