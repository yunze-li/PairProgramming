package com.duolingo.app.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.graphics.withSave

/** Custom drawable for animating auto advance indicator on a button. */
class AutoAdvanceIndicatorDrawable(
  indicatorColor: Int,
  alpha: Int,
) : Drawable() {

  private val paint =
    Paint(Paint.ANTI_ALIAS_FLAG).apply {
      style = Paint.Style.FILL
      color = indicatorColor
      this.alpha = alpha
    }

  // Animation step. Expected values in range [0, 1].
  private var step = 0f

  private val path = Path()

  override fun draw(canvas: Canvas) {
    val translationX = step * bounds.width()

    canvas.withSave {
      path.apply {
        reset()

        // Top left
        moveTo(0f, bounds.top.toFloat())
        // Top right
        rLineTo(translationX, 0f)
        // Bottom right
        rLineTo(0f, bounds.height().toFloat())
        // Bottom left
        rLineTo(-translationX, 0f)
        // Reconnect back to start
        rLineTo(0f, -bounds.height().toFloat())

        close()
      }
      drawPath(path, paint)
    }
  }
  /** Sets the animation step. Expected value range [0.0 1.0]. */
  fun setAnimationStep(animationStep: Float) {
    this.step = animationStep
  }

  override fun setAlpha(alpha: Int) {
    paint.alpha = alpha
  }

  override fun setColorFilter(colorFilter: ColorFilter?) {
    paint.colorFilter = colorFilter
  }

  override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}
