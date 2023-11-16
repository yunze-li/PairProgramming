package com.duolingo.data.network

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
//        val responseString = when {
//            uri.endsWith("starred") -> json
//            else -> ""
//        }
        val courseResponse = json

        return chain.proceed(chain.request())
            .newBuilder()
            .code(SUCCESS_CODE)
            .protocol(Protocol.HTTP_2)
            .message(courseResponse)
            .body(
                ResponseBody.create(
                MediaType.parse("application/json"),
                    courseResponse.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()
    }

    companion object {
        private const val SUCCESS_CODE = 200

        private const val json = """
            {
                "id": 1296269,
                "ui_language_id": "en",
                "learning_language_id": "dn"
            }
        """
    }
}