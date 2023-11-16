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
            uri.contains("duolingo-gateway/sessions/") -> allSessionsJson
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

        private const val SESSION_ID_1 = 100001L
        private const val SESSION_ID_2 = 100002L
        private const val SESSION_ID_3 = 100003L
        private const val SESSION_ID_4 = 100004L
        private const val SESSION_ID_5 = 100005L
        private const val SESSION_ID_6 = 100006L
        private const val SESSION_ID_7 = 100007L
        private const val SESSION_ID_8 = 100008L
        private const val SESSION_ID_9 = 100009L
        private const val allSessionsJson = """
            [{
                "id": $SESSION_ID_1,
                "difficulty": 2,
                "displayName": "travel",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_2,
                "difficulty": 2,
                "displayName": "family",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_3,
                "difficulty": 2,
                "displayName": "work",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_4,
                "difficulty": 2,
                "displayName": "movie",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_5,
                "difficulty": 2,
                "displayName": "music",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_6,
                "difficulty": 2,
                "displayName": "love",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_7,
                "difficulty": 2,
                "displayName": "sports",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_8,
                "difficulty": 2,
                "displayName": "restaurant",
                "isCompleted": false
            },
            {
                "id": $SESSION_ID_9,
                "difficulty": 2,
                "displayName": "cafe",
                "isCompleted": false
            }]
        """
    }
}