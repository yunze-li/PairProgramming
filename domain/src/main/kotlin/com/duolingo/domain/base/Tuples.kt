package com.duolingo.domain.base

/** Helper class representing tuples of various lengths. */
sealed class Tuples {

  /** Helper tuple class combining 4 generic types. */
  data class Tuple4<T1, T2, T3, T4>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
  ) : Tuples()

  /** Helper tuple class combining 5 generic types. */
  data class Tuple5<T1, T2, T3, T4, T5>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
    val fifth: T5,
  ) : Tuples()

  /** Helper tuple class combining 6 generic types. */
  data class Tuple6<T1, T2, T3, T4, T5, T6>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
    val fifth: T5,
    val sixth: T6,
  ) : Tuples()

  /** Helper tuple class combining 7 generic types. */
  data class Tuple7<T1, T2, T3, T4, T5, T6, T7>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
    val fifth: T5,
    val sixth: T6,
    val seventh: T7,
  ) : Tuples()

  /** Helper tuple class combining 8 generic types. */
  data class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
    val fifth: T5,
    val sixth: T6,
    val seventh: T7,
    val eighth: T8,
  ) : Tuples()

  /** Helper tuple class combining 9 generic types. */
  data class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
    val fifth: T5,
    val sixth: T6,
    val seventh: T7,
    val eighth: T8,
    val ninth: T9,
  ) : Tuples()
}
