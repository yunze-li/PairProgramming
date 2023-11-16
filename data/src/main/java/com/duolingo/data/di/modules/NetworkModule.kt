package com.duolingo.data.di.modules

import android.content.Context
import com.google.gson.Gson
import com.duolingo.data.di.providers.NetworkChecker
import com.duolingo.data.network.MockInterceptor
import com.duolingo.data.network.OkHttpClientFactory
import com.duolingo.data.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger module that provides Net class.
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    internal fun provideNetworkChecker(context: Context): NetworkChecker = NetworkChecker(context)

    @Provides
    @Singleton
    internal fun provideOkHttpClientFactory(): OkHttpClientFactory = OkHttpClientFactory()

    @Provides
    @Singleton
    internal fun provideRetrofit(
        context: Context,
        gson: Gson,
        okHttpClientFactory: OkHttpClientFactory,
        mockInterceptor: Interceptor,
    ): Retrofit =
        RetrofitFactory.getRetrofit(context, gson, okHttpClientFactory, mockInterceptor)

    @Provides
    @Singleton
    internal fun provideMockInterceptor(): Interceptor = MockInterceptor()

}