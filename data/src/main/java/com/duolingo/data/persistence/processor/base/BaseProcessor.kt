package com.duolingo.data.persistence.processor.base

import com.duolingo.data.persistence.dao.base.BaseDao
import com.duolingo.domain.utils.CheckPersistenceResultFunction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

abstract class BaseProcessor<T>(private val baseDao: BaseDao<T>) {

    fun insert(entity: T): Completable =
        Single.fromCallable { baseDao.insert(entity) > 0 }
            .flatMapCompletable(CheckPersistenceResultFunction())

    fun delete(entity: T): Completable =
        Single.fromCallable { baseDao.delete(entity) > 0 }
            .flatMapCompletable(CheckPersistenceResultFunction())

}