package com.duolingo.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duolingo.data.persistence.dao.RepoDao
import com.duolingo.data.persistence.entity.RepoEntity

@Database(entities = [(RepoEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

}
