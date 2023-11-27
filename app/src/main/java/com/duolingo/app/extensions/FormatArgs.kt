package com.duolingo.app.extensions

import android.content.Context
import androidx.core.text.BidiFormatter
import androidx.core.text.TextDirectionHeuristicsCompat
import com.duolingo.app.string.BidiFormatterProvider
import com.duolingo.app.ui.UiModel

/** Converts format args list to an Array, with any contained [UiModel]s resolved. */
internal fun List<Any>.toFormatArgsArray(
  context: Context,
  bidiFormatterProvider: BidiFormatterProvider,
): Array<Any> {
  val bidiFormatter = bidiFormatterProvider.default()
  return Array(size) {
    when (val arg = get(it)) {
      is UiModel<*> -> arg.resolve(context)
      is String -> bidiFormatter.wrap(arg)
      else -> arg
    }
  }
}

/**
 * Converts a list of pairs of format args and booleans to a modified version of the list, with any
 * contained [UiModel]s resolved
 */
internal fun List<Pair<Any, Boolean>>.toFormatArgsList(
  context: Context,
  bidiFormatterProvider: BidiFormatterProvider,
): List<Pair<Any, Boolean>> {
  val bidiFormatter = bidiFormatterProvider.default()
  return this.map { (arg, isLanguageVariable) ->
    when (arg) {
      is UiModel<*> -> arg.resolve(context) to isLanguageVariable
      is String -> bidiFormatter.wrap(arg) to isLanguageVariable
      else -> arg to isLanguageVariable
    }
  }
}

/** Wraps the given format arg to ensure that its dominant text direction is respected. */
private fun BidiFormatter.wrap(arg: String) =
  unicodeWrap(arg, TextDirectionHeuristicsCompat.ANYRTL_LTR)
