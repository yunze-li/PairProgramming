package com.duolingo.domain.model.id

import java.io.Serializable

/**
 * @param TOKEN The type of entity type which this id represents an instance of for type safety.
 */
data class LongId<TOKEN>(private val id: Long) : Serializable {

    fun get(): Long = id

    companion object {
        fun <TOKEN> empty(): LongId<TOKEN> = LongId(0L)
    }
}