package com.duolingo.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.duolingo.data.persistence.entity.CourseEntity.Companion.COURSE_ID
import com.duolingo.data.persistence.entity.SessionEntity.Companion.SESSION_TABLE

@Entity(tableName = SESSION_TABLE, foreignKeys = [ForeignKey(
    entity = CourseEntity::class,
    parentColumns = arrayOf(COURSE_ID),
    childColumns = arrayOf(COURSE_ID),
    onDelete = ForeignKey.CASCADE
)])
class SessionEntity(
    @PrimaryKey @ColumnInfo(name = SESSION_ID) val id: Long,
    @ColumnInfo(name = COURSE_ID) val courseId: Long,
    @ColumnInfo(name = SESSION_DIFFICULTY) val difficulty: Int,
) {

    companion object {
        // TABLE
        const val SESSION_TABLE = "session"

        // COLUMN
        const val SESSION_ID = "id"
        const val SESSION_DIFFICULTY = "difficulty"
    }

}
