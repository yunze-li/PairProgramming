package com.duolingo.app.ui

import android.content.Context
import java.io.Serializable

/** Wrapper around any resource requiring a [Context] for resolution. */
interface UiModel<R : Any> : Serializable {

  /** Resolves this [UiModel] for rendering. */
  fun resolve(context: Context): R
}
