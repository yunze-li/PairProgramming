package com.duolingo.data.converter

import com.duolingo.data.network.dto.SessionDTO
import com.duolingo.data.persistence.entity.CourseEntity
import com.duolingo.data.persistence.entity.SessionEntity
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Language
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

/**
 * A converter class used to convert [SessionDTO] or [SessionEntity] (in the data layer) to
 * [Session] in the domain layer and vice versa.
 */
class SessionConverter
@Inject constructor() {

    /**
     * Convert a [SessionDTO] into an [Session].
     * @param dto   Object to be transformed.
     * @return [Session] if [SessionDTO] is valid
     */
    fun convert(dto: SessionDTO): Session {
        return Session(
            id = LongId(dto.id),
//            challengeIds = dto.challengeIds,
            difficulty = dto.difficulty,
            displayName = dto.displayName,
            isCompleted = dto.isCompleted,
        )
    }

//    /**
//     * Convert a [SessionEntity] into an [Session].
//     * @param entity    Object to be transformed.
//     * @return [Session] if [SessionEntity] is valid, otherwise null.
//     */
//    fun convert(entity: SessionEntity): Session {
//        val uiLanguage = Language.fromLanguageId(entity.uiLanguageId)
//        val learningLanguage = Language.fromLanguageId(entity.learningLanguageId)
//        if (uiLanguage == null || learningLanguage == null) {
//            throw IllegalArgumentException("Invalid language found from CourseEntity, ui_language_id: ${entity.uiLanguageId}, learning_language_id: ${entity.learningLanguageId}")
//        } else {
//            return Session(
//                id = LongId(entity.id),
//                uiLanguage = uiLanguage,
//                learningLanguage = learningLanguage,
//            )
//        }
//    }
//    /**
//     * Convert a [Course] into an [CourseEntity].
//     * @param course    [Course] object to be transformed.
//     * @return [Course] if [CourseEntity] is valid, otherwise null.
//     */
//    fun convertToEntity(course: Course): CourseEntity =
//        CourseEntity(
//            id = course.id.get(),
//            userId = course.id.get(),
//            uiLanguageId = course.uiLanguage.languageId,
//            learningLanguageId = course.learningLanguage.languageId,
//        )
}