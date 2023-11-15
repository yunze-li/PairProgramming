package com.duolingo.domain.model

import com.duolingo.domain.model.id.LongId

/** Data model of a course, it will contains multiple sessions. */
data class Course(
    val id: LongId<Course>,
    val uiLanguage: Language,
    val learningLanguage: Language,
    val sessions: List<Session>? = null,
)
