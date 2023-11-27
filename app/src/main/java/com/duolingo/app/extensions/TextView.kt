package com.duolingo.app.extensions

import android.graphics.drawable.Drawable
import android.widget.TextView
import com.duolingo.app.ui.Color
import com.duolingo.app.ui.UiModel

/** Sets the displayed text to [text]. */
fun TextView.setText(text: UiModel<out CharSequence>?) {
  this.text = text?.resolve(context)
}

/** Sets the text color of the displayed text to [color]. */
fun TextView.setTextColor(color: UiModel<Color>): Unit =
  setTextColor(color.resolve(context).colorInt)

/** Sets the displayed hint text to [hint]. */
fun TextView.setHint(hint: UiModel<out CharSequence>?) {
  this.hint = hint?.resolve(context)
}
/** Sets the drawables (if any) to appear next to the text. */
fun TextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
  start: UiModel<Drawable>? = null,
  top: UiModel<Drawable>? = null,
  end: UiModel<Drawable>? = null,
  bottom: UiModel<Drawable>? = null,
) {
  setCompoundDrawablesRelativeWithIntrinsicBounds(
    start?.resolve(context),
    top?.resolve(context),
    end?.resolve(context),
    bottom?.resolve(context),
  )
}
