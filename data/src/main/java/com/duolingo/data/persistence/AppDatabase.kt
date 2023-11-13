package com.duolingo.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duolingo.data.persistence.dao.CourseDao
import com.duolingo.data.persistence.dao.UserDao
import com.duolingo.data.persistence.entity.CourseEntity
import com.duolingo.data.persistence.entity.UserEntity

@Database(
    entities =
    [
        UserEntity::class,
        CourseEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /** Get the DAO for the user data. */
    abstract fun userDao(): UserDao

    /** Get the DAO for the course data. */
    abstract fun courseDao(): CourseDao

}
