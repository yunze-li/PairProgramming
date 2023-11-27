package com.duolingo.app.string

import androidx.core.text.BidiFormatter
import java.io.Serializable

/** Provider for [BidiFormatter] instance. */
internal class BidiFormatterProvider : Serializable {

  /** Returns an instance of [BidiFormatter]. */
  fun default(): BidiFormatter = BidiFormatter.getInstance()

  /** Compares a bidi formatter provider against another object */
  override fun equals(other: Any?): Boolean = other is BidiFormatterProvider

  /** Set all bidi formatter provider instances' hashcode to 0 */
  override fun hashCode(): Int = 0
}
