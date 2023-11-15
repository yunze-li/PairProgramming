package com.duolingo.app.ui

import android.content.Context
import java.io.Serializable

/** Helper for [UiModel]s */
class UiModelHelper : Serializable {

  /** Convert [formatArgs] to an Array */
  fun convertToFormatArgsArray(context: Context, formatArgs: List<Any>): Array<Any> =
    Array(formatArgs.size) {
      when (val arg = formatArgs[it]) {
        is UiModel<*> -> arg.resolve(context)
        else -> arg
      }
    }
}
