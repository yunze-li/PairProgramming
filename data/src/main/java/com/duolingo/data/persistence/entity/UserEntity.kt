package com.duolingo.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duolingo.data.persistence.entity.UserEntity.Companion.USER_TABLE

@Entity(tableName = USER_TABLE)
class UserEntity(
    @PrimaryKey @ColumnInfo(name = USER_ID) val id: Long,
    @ColumnInfo(name = USER_NAME) val name: String,
    @ColumnInfo(name = USER_AGE) val age: Int,
    @ColumnInfo(name = USER_EMAIL) val email: String,
    @ColumnInfo(name = USER_IS_TRAIL_USER) val isTrialUser: Boolean,
    @ColumnInfo(name = USER_CURRENT_COURSE_ID) val currentCourseId: Long,
    @ColumnInfo(name = USER_LONGEST_STREAK) val longestStreak: Int,
    @ColumnInfo(name = USER_CURRENT_STREAK) val currentStreak: Int,
    @ColumnInfo(name = USER_XP) val xp: Long,
) {

    companion object {

        // TABLE
        const val USER_TABLE = "user"

        // COLUMN
        const val USER_ID = "user_id"
        const val USER_NAME = "name"
        const val USER_AGE = "age"
        const val USER_EMAIL = "email"
        const val USER_IS_TRAIL_USER = "isTrialUser"
        const val USER_CURRENT_COURSE_ID = "currentCourseId"
        const val USER_LONGEST_STREAK = "longest_streak"
        const val USER_CURRENT_STREAK = "current_streak"
        const val USER_XP = "xp"

        /**
         * Initialize mock user data
         */
        fun initializeMockUsers(): List<UserEntity> =
            listOf(
                UserEntity(
                    id = 1L,
                    name = "John Doe",
                    age = 25,
                    email = "johndoe@duolingo.com",
                    isTrialUser = false,
                    currentCourseId = 1L,
                    longestStreak = 35,
                    currentStreak = 35,
                    xp = 850L,
                ),
                UserEntity(
                    id = 2L,
                    name = "Bill Thomas",
                    age = 39,
                    email = "billthomas@duolingo.com",
                    isTrialUser = true,
                    currentCourseId = 3L,
                    longestStreak = 348,
                    currentStreak = 69,
                    xp = 39850L,
                ),
            )
    }

}
