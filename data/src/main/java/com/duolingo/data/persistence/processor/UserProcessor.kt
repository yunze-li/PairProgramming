package com.duolingo.data.persistence.processor

import com.duolingo.data.persistence.dao.UserDao
import com.duolingo.data.persistence.entity.UserEntity
import com.duolingo.data.persistence.processor.base.BaseProcessor
import com.duolingo.domain.model.User
import com.duolingo.domain.utils.CheckPersistenceResultFunction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProcessor
@Inject internal constructor(private val dao: UserDao) : BaseProcessor<UserEntity>(dao) {

    fun get(id: Long): Single<UserEntity> =
        Single.fromCallable { dao.get(id) }

    fun getAllUsers(): Single<List<UserEntity>> =
        Single.fromCallable { dao.getAllUsers() }

    fun updateUser(userEntity: UserEntity): Completable =
        dao.updateOrInsertUser(userEntity)
            .andThen { CheckPersistenceResultFunction() }

    fun updateCurrentCourse(id: Long, courseId: Long): Completable =
        dao.updateCurrentCourse(id, courseId = courseId)
            .andThen { CheckPersistenceResultFunction() }

}
