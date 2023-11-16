package com.duolingo.data.network

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val responseString = when {
            uri.contains("duolingo-gateway/courses/") -> allCoursesJson
            else -> ""
        }

        return chain.proceed(chain.request())
            .newBuilder()
            .code(SUCCESS_CODE)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                ResponseBody.create(
                MediaType.parse("application/json"),
                    responseString.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()
    }

    companion object {
        private const val SUCCESS_CODE = 200

        private const val COURSE_ID_1 = 10001L
        private const val COURSE_ID_2 = 10002L
        private const val COURSE_ID_3 = 10003L
        private const val COURSE_ID_4 = 10004L
        private const val COURSE_ID_5 = 10005L
        private const val allCoursesJson = """
            [{
                "id": $COURSE_ID_1,
                "ui_language_id": "en",
                "learning_language_id": "dn"
            },
            {
                "id": $COURSE_ID_2,
                "ui_language_id": "en",
                "learning_language_id": "ja"
            },
            {
                "id": $COURSE_ID_3,
                "ui_language_id": "ja",
                "learning_language_id": "zs"
            },
            {
                "id": $COURSE_ID_4,
                "ui_language_id": "zs",
                "learning_language_id": "ar"
            },
            {
                "id": $COURSE_ID_5,
                "ui_language_id": "en",
                "learning_language_id": "it"
            }]
        """
    }
}