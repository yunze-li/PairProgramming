package com.duolingo.rxjava.optional

/**
 * Optional that wraps a kotlin nullable, used to bypass RxJava's lack of support for null items.
 *
 * Background: https://github.com/ReactiveX/RxJava/issues/4644
 */
// Intentionally not a data class to avoid exposing the constructor
public class RxOptional<out T : Any> internal constructor(public val value: T?) {

  override fun equals(other: Any?): Boolean = other is RxOptional<*> && (other.value == value)

  override fun hashCode(): Int = value?.hashCode() ?: 0

  override fun toString(): String = "RxOptional(value=$value)"

  public operator fun component1(): T? = value

  public companion object {
    private val empty = RxOptional /* splinter ignore */(null)

    /** Returns a null instance. */
    public fun <T : Any> empty(): RxOptional<T> = empty
  }
}

/** Wraps any nullable instance in an [RxOptional]. */
public fun <T : Any> T?.toRxOptional(): RxOptional<T> =
  this?.let { RxOptional(it) } ?: RxOptional.empty()
