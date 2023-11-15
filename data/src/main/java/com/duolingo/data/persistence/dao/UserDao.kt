package com.duolingo.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duolingo.data.persistence.dao.base.BaseDao
import com.duolingo.data.persistence.entity.UserEntity
import com.duolingo.data.persistence.entity.UserEntity.Companion.USER_CURRENT_COURSE_ID
import com.duolingo.data.persistence.entity.UserEntity.Companion.USER_ID
import com.duolingo.data.persistence.entity.UserEntity.Companion.USER_TABLE
import com.duolingo.domain.model.User
import io.reactivex.rxjava3.core.Completable

/** Data Access Object for the [UserEntity] class. */
@Dao
abstract class UserDao : BaseDao<UserEntity> {

    /**
     * Select a user by the id
     * @param id The user id
     * @return A [UserEntity]
     */
    @Query("SELECT * FROM $USER_TABLE WHERE $USER_ID = :id")
    abstract fun get(id: Long): UserEntity

    /**
     * Get all stored users
     * @return A collection of [UserEntity]
     */
    @Query("SELECT * FROM $USER_TABLE")
    abstract fun getAllUsers(): List<UserEntity>

    /**
     * Update or insert [newUser]
     * @param newUser   The new [User] need to update or insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateOrInsertUser(newUser: UserEntity): Completable

    /**
     * Insert all users in [users]
     * @param users     All [User]s need to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllUsers(users: List<UserEntity>): Completable

    /**
     * Update user's current course
     * @param userId    The user id
     * @param courseId  The new current course id
     */
    @Query("UPDATE $USER_TABLE SET $USER_CURRENT_COURSE_ID = :courseId WHERE $USER_ID = :userId")
    abstract fun updateCurrentCourse(userId: Long, courseId: Long): Completable

}
