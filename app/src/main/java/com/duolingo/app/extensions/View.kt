package com.duolingo.app.extensions

import android.graphics.Typeface
import android.view.View
import com.duolingo.app.ui.DuoTypeface

/**
 * @return a boolean indicating whether `this` contains `other`. A view is considered to contain
 * itself.
 */
fun View.containsView(other: View): Boolean =
  if (this == other) true else (other.parent as? View)?.let { containsView(it) } ?: false

/**
 * Convenience method to get [DuoTypeface] styled font in views. This does not alter the font if the
 * view is in edit mode, as trying to invoke [DuoTypeface] methods will result in an exception.
 */
fun View.duoTypeface(typeface: Typeface?): Typeface? =
  if (isInEditMode) {
    typeface
  } else {
    if (typeface?.isBold == true) DuoTypeface.getDefaultBoldTypeface(context)
    else DuoTypeface.getDefaultTypeface(context)
  }
