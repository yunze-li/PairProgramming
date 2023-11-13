package com.duolingo.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duolingo.data.persistence.entity.CourseEntity.Companion.COURSE_TABLE

@Entity(tableName = COURSE_TABLE)
class CourseEntity(
    @PrimaryKey @ColumnInfo(name = COURSE_ID) val id: Long,
    @ColumnInfo(name = COURSE_UI_LANGUAGE_ID) val uiLanguageId: String,
    @ColumnInfo(name = COURSE_LEARNING_LANGUAGE_ID) val learningLanguageId: String,
) {

    companion object {
        // TABLE
        const val COURSE_TABLE = "course"

        // COLUMN
        const val COURSE_ID = "id"
        const val COURSE_UI_LANGUAGE_ID = "ui_language_id"
        const val COURSE_LEARNING_LANGUAGE_ID = "learning_language_id"
    }

}
