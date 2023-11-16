package com.duolingo.app.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.graphics.withSave

/** Custom drawable for animating shine effect. */
class ShineDrawable(val faceColor: Int) : Drawable() {

  private val paint =
    Paint(Paint.ANTI_ALIAS_FLAG).apply {
      style = Paint.Style.FILL
      color = Color.WHITE
    }

  // Stores shine line width in pixels.
  private var shineWidth = 0f

  // Stores offset of top and bottom x coordinates of shine line.
  private var offset = 0f

  // Shine animation step. Expected values in range [0, 1].
  private var step = 0f

  private val path = Path()

  override fun draw(canvas: Canvas) {
    shineWidth = bounds.width() * WIDTH_FACTOR
    offset = (bounds.height() / SLOPE).toFloat()

    val translationX = step * (bounds.width() + shineWidth + offset) - shineWidth - offset

    canvas.withSave {
      // Draw the face color, otherwise face will be transparent if face drawable is used.
      drawColor(faceColor)
      // Draw the shine effect on top of it.
      path.apply {
        reset()

        // Top left
        moveTo(bounds.left.toFloat() + offset * 2 + shineWidth + translationX, bounds.top.toFloat())
        // Top right
        rLineTo(shineWidth, 0f)
        // Bottom right
        rLineTo(-offset, bounds.height().toFloat())
        // Bottom left
        rLineTo(-shineWidth, 0f)
        // Reconnect back to start
        rLineTo(offset, -bounds.height().toFloat())

        close()
      }
      drawPath(path, paint)
    }
  }

  /** Sets the shine animation step. Expected value range [0.0 1.0]. */
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

  private companion object {
    // Defines the slope of the sine line: delta y / delta x
    private const val SLOPE = 1.3

    // Defines the width of the shine line as percentage of total drawable width.
    private const val WIDTH_FACTOR = 0.15f
  }
}
