package com.duolingo.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.duolingo.data.persistence.dao.base.BaseDao
import com.duolingo.data.persistence.entity.SessionEntity
import com.duolingo.data.persistence.entity.SessionEntity.Companion.SESSION_ID
import com.duolingo.data.persistence.entity.SessionEntity.Companion.SESSION_TABLE

/** Data Access Object for the [SessionEntity] class. */
@Dao
abstract class SessionDao : BaseDao<SessionEntity> {

    /**
     * Select a session by the id
     * @param id    The session id
     */
    @Query("SELECT * FROM $SESSION_TABLE WHERE $SESSION_ID = :id")
    abstract fun get(id: Long): SessionEntity

    /**
     * Get all stored sessions
     * @return A collection of [SessionEntity]
     */
    @Query("SELECT * FROM $SESSION_TABLE")
    abstract fun getAllSessions(): List<SessionEntity>

}
