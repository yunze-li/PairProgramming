package com.duolingo.data.persistence

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duolingo.data.R
import com.duolingo.data.persistence.dao.CourseDao
import com.duolingo.data.persistence.dao.SessionDao
import com.duolingo.data.persistence.dao.UserDao
import com.duolingo.data.persistence.entity.CourseEntity
import com.duolingo.data.persistence.entity.SessionEntity
import com.duolingo.data.persistence.entity.UserEntity

@Database(
    entities =
    [
        UserEntity::class,
        CourseEntity::class,
        SessionEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /** Get the DAO for the user data. */
    abstract fun userDao(): UserDao

    /** Get the DAO for the course data. */
    abstract fun courseDao(): CourseDao

    /** Get the DAO for the session. */
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    context.getString(R.string.DATABASE_NAME)
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
