package com.duolingo.data.persistence.processor

import com.duolingo.data.persistence.dao.CourseDao
import com.duolingo.data.persistence.entity.CourseEntity
import com.duolingo.data.persistence.processor.base.BaseProcessor
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.User
import com.duolingo.domain.utils.CheckPersistenceResultFunction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseProcessor
@Inject internal constructor(private val dao: CourseDao) : BaseProcessor<CourseEntity>(dao) {

    fun get(id: Long): Single<CourseEntity> = Single.fromCallable { dao.get(id) }

    fun getAllCourses(): Single<List<CourseEntity>> = Single.fromCallable { dao.getAllCourses() }

    fun updateCourse(courseEntity: CourseEntity): Completable =
        dao.updateOrInsertCourse(courseEntity)
            .andThen { CheckPersistenceResultFunction() }

}
