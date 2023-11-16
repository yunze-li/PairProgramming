package com.duolingo.data.network

import android.content.Context

import com.google.gson.Gson
import okhttp3.Interceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitFactory to generate a Retrofit instance.
 * It sets up request logging and a Gson type adapter.
 */
object RetrofitFactory {

    // Base URL: always ends with /
//    private const val URL_MAIN_WEBSERVICE = "https://duolingo.com/"
    private const val URL_MAIN_WEBSERVICE = "https://api.github.com/"

    /**
     * Get [Retrofit] instance.
     * @return instances of [Retrofit]
     */
    fun getRetrofit(
        context: Context,
        gson: Gson,
        okHttpClientFactory: OkHttpClientFactory,
        mockInterceptor: Interceptor,
    ): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL_MAIN_WEBSERVICE)
            .client(okHttpClientFactory.createOkHttpClient(context, mockInterceptor))
            .build()

}
