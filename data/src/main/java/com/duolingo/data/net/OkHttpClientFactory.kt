package com.duolingo.data.net

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.duolingo.data.BuildConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * OkHttp client factory
 */
open class OkHttpClientFactory {

    open fun createOkHttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    enableDebugTools(context)
                }
                updateTimeout()
            }
            .build()


    private fun OkHttpClient.Builder.enableDebugTools(context: Context) {
        addInterceptor(ChuckerInterceptor.Builder(context).build())
    }

    private fun OkHttpClient.Builder.updateTimeout(read: Long = 60, write: Long = 60) {
        readTimeout(read, TimeUnit.SECONDS)
        writeTimeout(write, TimeUnit.SECONDS)
    }

}