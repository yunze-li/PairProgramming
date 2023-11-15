package com.duolingo.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duolingo.data.persistence.dao.base.BaseDao
import com.duolingo.data.persistence.entity.CourseEntity
import com.duolingo.data.persistence.entity.CourseEntity.Companion.COURSE_ID
import com.duolingo.data.persistence.entity.CourseEntity.Companion.COURSE_TABLE
import com.duolingo.data.persistence.entity.SessionEntity
import com.duolingo.domain.model.Course
import io.reactivex.rxjava3.core.Completable

/** Data Access Object for the [CourseEntity] class. */
@Dao
abstract class SessionDao : BaseDao<SessionEntity> {

//    /**
//     * Select a course by the id
//     * @param id The course id
//     * @return A [CourseEntity]
//     */
//    @Query("SELECT * FROM $COURSE_TABLE WHERE $COURSE_ID = :id")
//    abstract fun get(id: Long): CourseEntity
//
//    /**
//     * Get all stored courses
//     * @return A collection of [CourseEntity]
//     */
//    @Query("SELECT * FROM $COURSE_TABLE")
//    abstract fun getAllCourses(): List<CourseEntity>
//
//    /**
//     * Update or insert [newCourse]
//     * @param newCourse   The new [Course] need to update or insert
//     */
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract fun updateOrInsertCourse(newCourseEntity: CourseEntity): Completable

}
