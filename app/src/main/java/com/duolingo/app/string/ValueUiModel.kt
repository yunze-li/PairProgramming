package com.duolingo.app.string

import android.content.Context
import com.duolingo.app.ui.UiModel

/** A [UiModel] for a string value needing no localization. */
internal data class ValueUiModel(
  private val literal: String,
) : UiModel<String> {
  override fun resolve(context: Context): String = literal
}
