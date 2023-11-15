package com.duolingo.app.ui

import android.animation.StateListAnimator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.updatePaddingRelative
import com.duolingo.app.R

/** A view with a bottom "lip" that disappears when pressed down, creating a 3D effect. */
public interface LipView {
  /** Value of paddingTop specified in XML. */
  public val internalPaddingTop: Int
    @Dimension get

  /** Value of paddingBottom specified in XML. */
  public val internalPaddingBottom: Int
    @Dimension get

  /** Width of an (inset) view border, typically nonzero iff you want a stroked appearance. */
  public val borderWidth: Int
    @Dimension get

  /** Outer corner radius. Subtract the border width to get the inner corner radius. */
  public val cornerRadius: Int
    @Dimension get

  /** The view's main color, which forms the backdrop for its contents. */
  public val faceColor: Int
    @ColorInt get

  /** Lip color. Also used as border color. */
  public val lipColor: Int
    @ColorInt get

  /** The view's main color when disabled, which forms the backdrop for its contents. */
  public val disabledFaceColor: Int
    @ColorInt get

  /** A drawable that can be used to draw on top of the view's face. */
  public val overlayDrawable: Drawable?
    get() = null

  /** A drawable that can be used as the backdrop for its contents. */
  public val faceDrawable: Drawable?
    get() = null

  /** A drawable that can be used as the lip */
  public val lipDrawable: Drawable?
    get() = null

  /**
   * Lip height. Must be at least as large as the view's bottom border, which is considered part of
   * the lip.
   */
  public val lipHeight: Int
    @Dimension get

  /** Position of view in a series of view. Index of [Position]. */
  public val position: Position

  /** Whether the disabled state should look any different from the enabled state. */
  public val shouldStyleDisabledState: Boolean
    get() = true

  /** Whether the disabled state should appear dimmed or the default gray. */
  public val dimWhenDisabled: Boolean
    get() = false

  /** Pressed progress */
  public val pressedProgress: Float?
    get() = null

  /** Transitional background for animating color change */
  public val transitionalInnerBackground: TransitionalInnerBackground?
    get() = null

  private fun getPressedOffset(isPressed: Boolean): Int {
    val pressedProgress = pressedProgress ?: if (isPressed) 1F else 0F
    return ((lipHeight - borderWidth) * pressedProgress).toInt()
  }

  /**
   * Creates a fake-3D background for a [LipView]. https://stackoverflow.com/a/20138871
   * https://stackoverflow.com/a/34236837
   */
  public fun drawBackground(
    @ColorInt faceColor: Int = this.faceColor,
    @ColorInt lipColor: Int = this.lipColor,
    borderWidth: Int = this.borderWidth,
    @ColorInt disabledFaceColor: Int = this.disabledFaceColor,
    faceDrawable: Drawable? = this.faceDrawable,
    lipDrawable: Drawable? = this.lipDrawable,
    transitionalInnerBackground: TransitionalInnerBackground? = this.transitionalInnerBackground,
    overlayDrawable: Drawable? = this.overlayDrawable,
  ) {
    this as? View ?: throw RuntimeException("LipView implementer must be a View")

    background =
      StateListDrawable().apply {
        val getRekt: (FloatArray, Int, Drawable?) -> ShapeDrawable =
          { outerRadii, color, faceDrawable ->
            if (faceDrawable != null) {
              ShapeDrawable(RoundRectShape(outerRadii, null, null)).apply {
                shaderFactory =
                  object : ShapeDrawable.ShaderFactory() {
                    override fun resize(width: Int, height: Int): Shader? {
                      // The app will crash if we try to create a bitmap with a width or height <= 0
                      if (width <= 0 || height <= 0) {
                        return null
                      }
                      val drawableBgBitmap =
                        faceDrawable.toBitmap(width, height, Bitmap.Config.ARGB_8888)
                      return BitmapShader(
                        drawableBgBitmap,
                        Shader.TileMode.CLAMP,
                        Shader.TileMode.CLAMP
                      )
                    }
                  }
              }
            } else {
              ShapeDrawable(RoundRectShape(outerRadii, null, null)).apply { paint.color = color }
            }
          }

        fun LayerDrawable.addTransitionalBackground(pressedOffset: Int) {
          if (transitionalInnerBackground == null) {
            return
          }
          val inset = transitionalInnerBackground.distanceFromBorder
          val index =
            addLayer(
              getRekt(
                position.getOuterRadii(cornerRadius - borderWidth),
                transitionalInnerBackground.color.resolve(context).colorInt,
                null
              )
            )
          setLayerInset(
            index,
            borderWidth + inset,
            pressedOffset + borderWidth + inset,
            borderWidth + inset,
            lipHeight - pressedOffset + inset
          )
        }

        // Disabled state
        if (shouldStyleDisabledState &&
            faceColor != ContextCompat.getColor(context, R.color.juicyTransparent)
        ) {
          val disabledBackgroundColor =
            if (dimWhenDisabled) {
              ColorUtils.setAlphaComponent(lipColor, 26)
            } else disabledFaceColor

          addState(
            intArrayOf(-android.R.attr.state_enabled),
            LayerDrawable(
                arrayOf(
                  getRekt(position.getOuterRadii(cornerRadius), disabledBackgroundColor, null)
                )
              )
              .apply {
                val inset0 = position.getInsetRect(0, lipHeight - borderWidth, 0, 0)
                setLayerInset(0, inset0.left, inset0.top, inset0.right, inset0.bottom)
              }
          )
        }

        // Pressed state
        addState(
          intArrayOf(android.R.attr.state_pressed),
          LayerDrawable(
              arrayOf(
                getRekt(position.getOuterRadii(cornerRadius), lipColor, lipDrawable),
                getRekt(
                  position.getOuterRadii(cornerRadius - borderWidth),
                  faceColor,
                  faceDrawable
                ),
                getRekt(
                  position.getOuterRadii(cornerRadius - borderWidth),
                  ContextCompat.getColor(context, R.color.juicyTransparent),
                  overlayDrawable
                ),
              )
            )
            .apply {
              val offset = getPressedOffset(isPressed = true)
              val inset0 = position.getInsetRect(0, offset, 0, 0)
              setLayerInset(0, inset0.left, inset0.top, inset0.right, inset0.bottom)
              val inset1 =
                position.getInsetRect(
                  borderWidth,
                  offset + borderWidth,
                  borderWidth,
                  lipHeight - offset
                )
              setLayerInset(1, inset1.left, inset1.top, inset1.right, inset1.bottom)
              setLayerInset(2, inset1.left, inset1.top, inset1.right, inset1.bottom)
              addTransitionalBackground(offset)
            }
        )

        // Unpressed + selected state
        addState(
          intArrayOf(android.R.attr.state_selected),
          LayerDrawable(
              arrayOf(
                getRekt(position.getOuterRadii(cornerRadius), lipColor, lipDrawable),
                getRekt(
                  position.getOuterRadii(cornerRadius - borderWidth),
                  faceColor,
                  faceDrawable
                ),
                getRekt(
                  position.getOuterRadii(cornerRadius - borderWidth),
                  ContextCompat.getColor(context, R.color.juicyTransparent),
                  overlayDrawable
                ),
              )
            )
            .apply {
              val offset = getPressedOffset(isPressed = false)
              // If selected, ignore inset rect to ensure same width outline
              setLayerInset(0, 0, offset, 0, 0)
              setLayerInset(1, borderWidth, offset + borderWidth, borderWidth, lipHeight - offset)
              setLayerInset(2, borderWidth, offset + borderWidth, borderWidth, lipHeight - offset)
              addTransitionalBackground(offset)
            }
        )

        // Unpressed state
        addState(
          intArrayOf(),
          LayerDrawable(
              arrayOf(
                getRekt(position.getOuterRadii(cornerRadius), lipColor, lipDrawable),
                getRekt(
                  position.getOuterRadii(cornerRadius - borderWidth),
                  faceColor,
                  faceDrawable
                ),
                getRekt(
                  position.getOuterRadii(cornerRadius - borderWidth),
                  ContextCompat.getColor(context, R.color.juicyTransparent),
                  overlayDrawable
                ),
              )
            )
            .apply {
              val offset = getPressedOffset(isPressed = false)
              val inset0 = position.getInsetRect(0, offset, 0, 0)
              setLayerInset(0, inset0.left, inset0.top, inset0.right, inset0.bottom)
              val inset1 =
                position.getInsetRect(
                  borderWidth,
                  offset + borderWidth,
                  borderWidth,
                  lipHeight - offset
                )
              setLayerInset(1, inset1.left, inset1.top, inset1.right, inset1.bottom)
              setLayerInset(2, inset1.left, inset1.top, inset1.right, inset1.bottom)
              addTransitionalBackground(offset)
            }
        )
      }
    updateContentPosition()
  }

  /** Updates the position of the view's contents based on its current state. */
  @CallSuper
  public fun updateContentPosition() {
    this as? View ?: throw RuntimeException("LipView implementer must be a View")

    // Repositions a [LipView]'s contents so that they remain centered on the view's face as it
    // moves "up" and "down" along the z-axis. https://stackoverflow.com/a/21223137
    val multiplier =
      pressedProgress?.let { it * 2 - 1 }
        ?: if (isPressed || shouldStyleDisabledState && !isEnabled) 1F else -1F
    val paddingTopToAdd = (multiplier * (lipHeight - borderWidth) / 2).toInt()
    this.updatePaddingRelative(
      paddingStart,
      internalPaddingTop + paddingTopToAdd,
      paddingEnd,
      internalPaddingBottom - paddingTopToAdd
    )
  }

  /**
   * Smoothly animates the face down and up when it's pressed
   *
   * @see CardView.animatePress this method should be used instead to avoid the initial un-pressed
   * state animating
   */
  public fun addPressAnimator(setPressedProgress: (Float?) -> Unit) {
    this as? View ?: throw RuntimeException("LipView implementer must be a View")

    fun ValueAnimator.asPressedAnimator() = apply {
      duration = 100
      addUpdateListener { setPressedProgress(it.animatedValue as? Float) }
    }

    stateListAnimator =
      (stateListAnimator ?: StateListAnimator()).apply {
        addState(
          intArrayOf(android.R.attr.state_pressed),
          ValueAnimator.ofFloat(0F, 1F).asPressedAnimator()
        )
        addState(
          intArrayOf(-android.R.attr.state_pressed),
          ValueAnimator.ofFloat(1F, 0F).asPressedAnimator()
        )
      }
  }

  /** Enum of view position, relative to a group of related views. */
  public enum class Position(
    private val radiusMultipliers: FloatArray,
    private val insetMultipliers: FloatArray
  ) {
    // Note: enum values correspond to "position" attribute values in juicy_attrs.xml.
    // If you modify the values you have to modify "position" attribute to match as well.
    BOTTOM(
      FloatArray(4) { 0f } + FloatArray(4) { 1f }, // Round bottom corners
      floatArrayOf(1f, 0.5f, 1f, 1f) // Half top border
    ),
    BOTTOM_NO_TOP(
      FloatArray(4) { 0f } + FloatArray(4) { 1f }, // Round bottom corners
      floatArrayOf(1f, 0f, 1f, 1f) // No top border
    ),
    CENTER_VERTICAL(
      FloatArray(8) { 0f }, // Round no corners
      floatArrayOf(1f, 0.5f, 1f, 0.5f) // Half start & end borders
    ),
    CENTER_VERTICAL_NO_TOP(
      FloatArray(8) { 0f }, // Round no corners
      floatArrayOf(1f, 0f, 1f, 0.5f) // No top and half bottom border
    ),
    NONE(
      FloatArray(8) { 1f }, // Round all corners
      floatArrayOf(1f, 1f, 1f, 1f) // Full borders
    ),
    TOP(
      FloatArray(4) { 1f } + FloatArray(4) { 0f }, // Round top corners
      floatArrayOf(1f, 1f, 1f, 0.5f) // Half bottom border
    ),
    TOP_NO_BOTTOM(
      FloatArray(4) { 1f } + FloatArray(4) { 0f }, // Round top corners
      floatArrayOf(1f, 1f, 1f, 0f)
    ),
    @Suppress("unused")
    TOP_FULL_BOTTOM(
      FloatArray(4) { 1f } + FloatArray(4) { 0f }, // Round top corners
      floatArrayOf(1f, 1f, 1f, 1f) // Full border
    ),
    @Suppress("unused")
    LEFT(
      FloatArray(2) { 1f } + FloatArray(4) { 0f } + FloatArray(2) { 1f }, // Round left corners
      floatArrayOf(1f, 1f, 0.5f, 1f) // Half right border
    ),
    @Suppress("unused")
    RIGHT(
      FloatArray(2) { 0f } + FloatArray(4) { 1f } + FloatArray(2) { 0f }, // Round right corners
      floatArrayOf(0.5f, 1f, 1f, 1f) // Half left border
    ),
    TOP_RIGHT(
      FloatArray(6) { 1f } +
        FloatArray(2) {
          0.3f
        }, // Completely round all corners except bottom left, which is partially rounded
      floatArrayOf(1f, 1f, 1f, 1f) // Full border
    ),
    TOP_RIGHT_MORE_ROUNDED(
      FloatArray(6) { 1f } +
        FloatArray(2) {
          0.5f
        }, // Completely round all corners except bottom left, which is partially rounded
      floatArrayOf(1f, 1f, 1f, 1f) // Full border
    ),
    TOP_LEFT(
      FloatArray(4) { 1f } +
        FloatArray(2) { 0.3f } +
        FloatArray(2) {
          1f
        }, // Completely round all corners except bottom right, which is partially rounded
      floatArrayOf(1f, 1f, 1f, 1f) // Full border
    ),
    TOP_LEFT_MORE_ROUNDED(
      FloatArray(4) { 1f } +
        FloatArray(2) { 0.5f } +
        FloatArray(2) {
          1f
        }, // Completely round all corners except bottom right, which is partially rounded
      floatArrayOf(1f, 1f, 1f, 1f) // Full border
    ),
    NO_BORDER(
      FloatArray(8) { 0f }, // Round no corners
      floatArrayOf(1f, 0f, 1f, 0f) // Half top border
    ),
    TOP_RIGHT_BOTTOM_LEFT(
      floatArrayOf(1f, 1f, 0f, 0f, 0.5f, 0.5f, 0f, 0f), // Round top left and bottom right corner
      floatArrayOf(1f, 1f, 1f, 1f)
    ),
    ;

    /** Returns FloatArray representing radius values for RoundRectShape's outer roundrect. */
    public fun getOuterRadii(outerRadius: Int): FloatArray =
      FloatArray(8) { i -> outerRadius.toFloat() * radiusMultipliers[i] }

    /** Returns Rect representing the insets of pixels for the drawable. */
    public fun getInsetRect(left: Int, top: Int, right: Int, bottom: Int): Rect =
      Rect(
        (left.toFloat() * insetMultipliers[0]).toInt(),
        (top.toFloat() * insetMultipliers[1]).toInt(),
        (right.toFloat() * insetMultipliers[2]).toInt(),
        (bottom.toFloat() * insetMultipliers[3]).toInt()
      )

    public companion object {
      /** Returns Position from enum index. */
      public fun valueOf(value: Int): Position =
        if (value in values().indices) values()[value] else NONE
    }
  }

  /** Smaller rectangle within the button face that can be used to animate between face colors */
  public data class TransitionalInnerBackground(
    val color: UiModel<Color>,
    val distanceFromBorder: Int,
  )
}
