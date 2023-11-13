package com.duolingo.domain.model.id

import java.io.Serializable

/**
 * @param TOKEN The type of entity type which this id represents an instance of for type safety.
 */
data class StringId<TOKEN>(private val id: String) : Serializable {

    /** Gets the ID value. */
    fun get(): String = id

    companion object {}
}