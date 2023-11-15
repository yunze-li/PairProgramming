package com.duolingo.app.extensions

import android.view.View
import android.view.ViewGroup

/** Recursively generates a [Sequence] of all of a [ViewGroup]'s descendant [View] s. */
fun ViewGroup.getDescendantViews(): Sequence<View> =
  (0 until childCount).asSequence().flatMap {
    when (val child = getChildAt(it)) {
      is ViewGroup -> child.getDescendantViews()
      else -> sequenceOf(child)
    }
  }
