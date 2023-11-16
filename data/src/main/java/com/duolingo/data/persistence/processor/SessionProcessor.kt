package com.duolingo.data.persistence.processor

import com.duolingo.data.persistence.dao.SessionDao
import com.duolingo.data.persistence.entity.SessionEntity
import com.duolingo.data.persistence.processor.base.BaseProcessor
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionProcessor
@Inject internal constructor(private val dao: SessionDao) : BaseProcessor<SessionEntity>(dao) {

    fun getSession(id: Long): Single<SessionEntity> = Single.fromCallable { dao.get(id) }

    fun getAllSessions(): Single<List<SessionEntity>> = Single.fromCallable { dao.getAllSessions() }

}
