package com.duolingo.app.string

import android.content.Context
import androidx.annotation.PluralsRes
import com.duolingo.app.extensions.toFormatArgsArray
import com.duolingo.app.ui.UiModel

/** A [UiModel] for our plurals resources. */
internal data class PluralsResUiModel(
  @PluralsRes private val resId: Int,
  private val quantity: Int,
  private val formatArgs: List<Any>,
  private val bidiFormatterProvider: BidiFormatterProvider,
) : UiModel<String> {
  override fun resolve(context: Context): String =
    context.resources.getQuantityString(
      resId,
      quantity,
      *formatArgs.toFormatArgsArray(context, bidiFormatterProvider),
    )
}
