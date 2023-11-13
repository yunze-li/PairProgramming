package com.duolingo.data.net.api

import android.content.Context
import com.google.gson.Gson
import com.duolingo.data.net.OkHttpClientFactoryTest
import com.duolingo.data.net.RetrofitFactory
import io.mockk.mockk
import org.junit.Test

class DuoApiTest {

    private val context = mockk<Context>()
    private val retrofit = RetrofitFactory.getRetrofit(context, Gson(), OkHttpClientFactoryTest())

    private val service: DuoApi
        get() = retrofit.create(DuoApi::class.java)

    @Test
    fun getUserWebservice() {
        service.getUser(1234567L).test()
            .assertNoErrors()
    }
}
