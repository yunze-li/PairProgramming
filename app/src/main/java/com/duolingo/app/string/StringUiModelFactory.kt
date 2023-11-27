package com.duolingo.app.string

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.duolingo.app.ui.UiModel

/** Factory for creating String-backed [UiModel] s. */
class StringUiModelFactory
internal constructor(
  private val bidiFormatterProvider: BidiFormatterProvider,
) {

  /** Creates a new instance of [StringUiModelFactory]. */
  constructor() :
    this(
      bidiFormatterProvider = BidiFormatterProvider(),
    )

  /** Creates a new [UiModel] for our string resources. */
  fun stringRes(
    @StringRes resId: Int,
    vararg formatArgs: Any,
  ): UiModel<String> =
    StringResUiModel(
      resId = resId,
      formatArgs = formatArgs.toList(),
      bidiFormatterProvider = bidiFormatterProvider,
    )

  /** Creates a new [UiModel] for our plurals resources. */
  fun pluralsRes(
    @PluralsRes resId: Int,
    quantity: Int,
    vararg formatArgs: Any,
  ): UiModel<String> =
    PluralsResUiModel(
      resId = resId,
      quantity = quantity,
      formatArgs = formatArgs.toList(),
      bidiFormatterProvider = bidiFormatterProvider,
    )

  /** Creates a new [UiModel] for a string value needing no localization. */
  fun value(
    literal: String,
  ): UiModel<String> = ValueUiModel(literal)

  /** Creates a new [UiModel] backed by an empty string needing no localization. */
  fun empty(): UiModel<String> = ValueUiModel("")
}
