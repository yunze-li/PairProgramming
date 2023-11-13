package com.duolingo.data.converter

import com.duolingo.data.net.dto.CourseDTO
import com.duolingo.data.persistence.entity.CourseEntity
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Language
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

/**
 * A converter class used to convert [CourseDTO] or [CourseEntity] (in the data layer) to [Course]
 * in the domain layer and vice versa.
 */
class CourseConverter
@Inject constructor() {

    /**
     * Convert a [CourseDTO] into an [Course].
     * @param dto   Object to be transformed.
     * @return [Course] if [CourseDTO] is valid
     */
    fun convert(dto: CourseDTO): Course {
        val uiLanguage = Language.fromLanguageId(dto.uiLanguageId)
        val learningLanguage = Language.fromLanguageId(dto.uiLanguageId)
        if (uiLanguage == null || learningLanguage == null) {
             throw IllegalArgumentException("Invalid language found from CourseDTO, ui_language_id: ${dto.uiLanguageId}, learning_language_id: ${dto.learningLanguageId}")
        } else {
            return Course(
                id = LongId(dto.id),
                uiLanguage = uiLanguage,
                learningLanguage = learningLanguage,
            )
        }
    }

//    /**
//     * Convert a Collection of [CourseDTO] into a List of [Course].
//     * @param dtoCollection Object Collection to be transformed.
//     * @return list of [Course]
//     */
//    fun convert(dtoCollection: Collection<CourseDTO>): List<Course> =
//        dtoCollection.map { convert(it) }

    /**
     * Convert a [CourseEntity] into an [Course].
     * @param entity    Object to be transformed.
     * @return [Course] if [CourseEntity] is valid, otherwise null.
     */
    fun convert(entity: CourseEntity): Course {
        val uiLanguage = Language.fromLanguageId(entity.uiLanguageId)
        val learningLanguage = Language.fromLanguageId(entity.learningLanguageId)
        if (uiLanguage == null || learningLanguage == null) {
            throw IllegalArgumentException("Invalid language found from CourseEntity, ui_language_id: ${entity.uiLanguageId}, learning_language_id: ${entity.learningLanguageId}")
        } else {
            return Course(
                id = LongId(entity.id),
                uiLanguage = uiLanguage,
                learningLanguage = learningLanguage,
            )
        }
    }

//    /**
//     * Convert a Collection of [CourseEntity] into a List of [Course].
//     * @param entityCollection Object Collection to be transformed.
//     * @return list of [Course]
//     */
//    fun convert(entityCollection: Collection<CourseEntity>): List<Course> =
//        entityCollection.map { convert(it) }

    /**
     * Convert a [Course] into an [CourseEntity].
     * @param course    [Course] object to be transformed.
     * @return [Course] if [CourseEntity] is valid, otherwise null.
     */
    fun convertToEntity(course: Course): CourseEntity =
        CourseEntity(
            id = course.id.get(),
            uiLanguageId = course.uiLanguage.languageId,
            learningLanguageId = course.learningLanguage.languageId,
        )

    /**
     * Convert a Collection of [Course] into a List of [CourseEntity].
     * @param modelCollection Object Collection to be transformed.
     * @return list of [CourseEntity]
     */
    fun convertToEntity(modelCollection: Collection<Course>): List<CourseEntity> =
        modelCollection.map { convertToEntity(it) }
}