package com.duolingo.app.ui

import androidx.annotation.ColorInt

/**
 * Simple wrapper around Android's color integers to avoid incorrect types when passing around typed
 * [UiModel] s for colors.
 */
data class Color(@ColorInt val colorInt: Int)
