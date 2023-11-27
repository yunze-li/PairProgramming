package com.duolingo.app.string
import android.content.Context
import androidx.annotation.StringRes
import com.duolingo.app.extensions.toFormatArgsArray
import com.duolingo.app.ui.UiModel

/** A [UiModel] for our string resources. */
internal data class StringResUiModel(
  @StringRes private val resId: Int,
  private val formatArgs: List<Any>,
  private val bidiFormatterProvider: BidiFormatterProvider,
) : UiModel<String> {
  override fun resolve(context: Context): String =
    when (formatArgs.size) {
      0 -> context.resources.getString(resId)
      else ->
        context.resources.getString(
          resId,
          *formatArgs.toFormatArgsArray(context, bidiFormatterProvider),
        )
    }
}
