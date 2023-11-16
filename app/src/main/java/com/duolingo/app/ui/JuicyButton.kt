package com.duolingo.app.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.duolingo.app.R
import com.duolingo.app.extensions.duoTypeface
import com.duolingo.app.extensions.loopForever
import java.util.Locale

/** A button with a bottom "lip" that disappears when pressed down, creating a 3D effect. */
class JuicyButton
@JvmOverloads
constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0,
) : AppCompatTextView(context, attrs, defStyle), LipView {

  override val internalPaddingTop: Int = paddingTop
  override val internalPaddingBottom: Int = paddingBottom
  override var borderWidth: Int = 0
    private set
  override var cornerRadius: Int = 0
    private set
  override var dimWhenDisabled: Boolean = false
    private set
  override var shouldStyleDisabledState: Boolean = true
    private set
  override var faceColor: Int = 0
    private set
  override var lipColor: Int = 0
    private set
  override var disabledFaceColor: Int = ContextCompat.getColor(context, R.color.juicySwan)
    private set
  override var overlayDrawable: Drawable? = null
    private set
  override var faceDrawable: Drawable? = null
    private set
  override var lipHeight: Int = 0
    private set
  override var position: LipView.Position = LipView.Position.NONE
    private set

  private var isAllCapsInternal: Boolean = false
  private val initialTextColors = textColors

  var autoAdvanceIndicatorAnimator: ValueAnimator? = null
    set(value) {
      field?.cancel()
      field = value
    }

  // Avoids unnecessary paint.getTextBounds() calls
  private var textBounds: Rect? = null
    private set
  // Avoids getCompoundDrawables() calls, each call creates a new drawable array
  private var startDrawable: Drawable? = null
  private var endDrawable: Drawable? = null

  private var textBackup: CharSequence? = null
  private var ignoreRecursiveTextChange = false
  @ColorInt
  private var progressTintColor: Int = ContextCompat.getColor(context, R.color.juicyMacaw)

  private var selectedFaceColor: Int = 0
  private var selectedLipColor: Int = 0
  private var selectedBorderWidth: Int = 0

  private var disabledTextColor: Int = ContextCompat.getColor(context, R.color.juicyHare)

  private var sideDrawableTranslation: Float = 0f

  init {
    textDirection = View.TEXT_DIRECTION_LOCALE
    // Get custom attributes
    attrs ?: throw RuntimeException("Null attributes")

    context.withStyledAttributes(attrs, R.styleable.LipView, defStyle, 0) {
      borderWidth = getDimensionPixelSize(R.styleable.LipView_borderWidth, borderWidth)
      cornerRadius = getDimensionPixelSize(R.styleable.LipView_cornerRadius, cornerRadius)
      dimWhenDisabled = getBoolean(R.styleable.LipView_dimWhenDisabled, dimWhenDisabled)
      faceColor = getColor(R.styleable.LipView_faceColor, faceColor)
      lipColor = getColor(R.styleable.LipView_lipColor, lipColor)
      faceDrawable = getDrawable(R.styleable.LipView_faceDrawable)
      disabledFaceColor = getColor(R.styleable.LipView_disabledFaceColor, disabledFaceColor)
      disabledTextColor = getColor(R.styleable.LipView_disabledTextColor, disabledTextColor)
      selectedFaceColor = getColor(R.styleable.LipView_selectedFaceColor, selectedFaceColor)
      selectedLipColor = getColor(R.styleable.LipView_selectedLipColor, selectedLipColor)
      selectedBorderWidth =
        getDimensionPixelSize(R.styleable.LipView_selectedBorderWidth, selectedBorderWidth)
      lipHeight = maxOf(getDimensionPixelSize(R.styleable.LipView_lipHeight, 0), borderWidth)
      position = LipView.Position.valueOf(getInt(R.styleable.LipView_position, -1))
    }

    context.withStyledAttributes(attrs, R.styleable.JuicyButton, defStyle, 0) {
      progressTintColor =
        getColor(R.styleable.JuicyButton_progressIndicatorTint, progressTintColor)
    }

    // Get parent attributes
    context.withStyledAttributes(attrs, intArrayOf(android.R.attr.textAllCaps), defStyle, 0) {
      isAllCapsInternal = getBoolean(0, isAllCapsInternal)
    }

    cacheDrawablesAndSetGravity()
    drawBackground()
    updateTextColor()
    measureText()
  }

  private val progressDrawable by lazy {
    AnimatedVectorDrawableCompat.create(context, R.drawable.dot_small_progress_avd)?.apply {
      DrawableCompat.setTint(this, progressTintColor)
      loopForever(this@JuicyButton)
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
  }

  var showProgress: Boolean = false
    set(value) {
      if (field == value) {
        return
      }
      field = value
      if (value) {
        try {
          textBackup = text
          ignoreRecursiveTextChange = true
          text = null
        } finally {
          ignoreRecursiveTextChange = false
        }
        super.setCompoundDrawablesRelative(progressDrawable, null, null, null)
        progressDrawable?.start()
      } else {
        if (textBackup != null) text = textBackup
        progressDrawable?.stop()
        if (!isInEditMode) {
          require(startDrawable == null || endDrawable == null) {
            "Buttons with multiple drawables not supported"
          }
        }
        super.setCompoundDrawablesRelative(startDrawable, null, endDrawable, null)
      }
      updateGravityForDrawables()
    }

  /** Updates background attributes and redraws the background */
  fun updateBackground(
    dimWhenDisabled: Boolean = this.dimWhenDisabled,
    shouldStyleDisabledState: Boolean = this.shouldStyleDisabledState,
    faceColor: Int = this.faceColor,
    position: LipView.Position = this.position,
    lipColor: Int = this.lipColor,
    disabledFaceColor: Int = this.disabledFaceColor,
    faceDrawable: Drawable? = this.faceDrawable,
    overlayDrawable: Drawable? = null,
  ) {
    this.dimWhenDisabled = dimWhenDisabled
    this.shouldStyleDisabledState = shouldStyleDisabledState
    this.faceColor = faceColor
    this.lipColor = lipColor
    this.disabledFaceColor = disabledFaceColor
    this.position = position
    this.faceDrawable = faceDrawable
    this.overlayDrawable = overlayDrawable
    drawBackground()
  }

  private fun updateTextColor() {
    // this gets called from setEnabled which can get called in super.init, so instance vars may be
    // null
    if (isEnabled) setTextColor(initialTextColors ?: return)
    else
      when {
        dimWhenDisabled -> setTextColor(ColorUtils.setAlphaComponent(lipColor, 51))
        shouldStyleDisabledState -> setTextColor(disabledTextColor)
        else -> setTextColor(initialTextColors ?: return)
      }
  }

  private fun cacheDrawablesAndSetGravity() {
    val (start, _, end) = compoundDrawablesRelative

    startDrawable = start
    endDrawable = end

    updateGravityForDrawables()
  }

  private fun updateGravityForDrawables() {
    // Choose the gravity based on drawable position. TextView drawables are always positioned flush
    // to the view edge, so gravity start/end is used to ensure drawable and text are side-by-side.
    gravity =
      when {
        startDrawable != null || showProgress -> Gravity.START or Gravity.CENTER_VERTICAL
        endDrawable != null -> Gravity.END or Gravity.CENTER_VERTICAL
        else -> Gravity.CENTER
      }
  }

  override fun setText(text: CharSequence?, type: BufferType?) {
    super.setText(text, type)

    if (showProgress && !ignoreRecursiveTextChange) {
      try {
        ignoreRecursiveTextChange = true
        textBackup = text
        this.text = null
      } finally {
        ignoreRecursiveTextChange = false
      }
    }
  }

  override fun setSelected(selected: Boolean) {
    val lipColorToSet =
      if (selected) {
        selectedLipColor
      } else {
        lipColor
      }

    val faceColorToSet =
      if (selected) {
        selectedFaceColor
      } else {
        faceColor
      }

    val borderWidthToSet =
      if (selected) {
        selectedBorderWidth
      } else {
        borderWidth
      }

    drawBackground(faceColorToSet, lipColorToSet, borderWidthToSet)

    super.setSelected(selected)
  }

  override fun setCompoundDrawables(
    left: Drawable?,
    top: Drawable?,
    right: Drawable?,
    bottom: Drawable?
  ) {
    super.setCompoundDrawables(left, top, right, bottom)
    cacheDrawablesAndSetGravity()
  }

  override fun setCompoundDrawablesRelative(
    start: Drawable?,
    top: Drawable?,
    end: Drawable?,
    bottom: Drawable?
  ) {
    super.setCompoundDrawablesRelative(start, top, end, bottom)
    cacheDrawablesAndSetGravity()
  }

  override fun setCompoundDrawablePadding(pad: Int) {
    if (pad == compoundDrawablePadding) {
      // Avoid unnecessary redraw
      return
    }
    super.setCompoundDrawablePadding(pad)
  }

  /**
   * Set whether the button is enabled. Note that the content position and text color are updated.
   */
  override fun setEnabled(enabled: Boolean) {
    // updateContentPosition will cause a redraw, so exit early.
    if (this.isEnabled == enabled) {
      return
    }
    super.setEnabled(enabled)
    updateContentPosition()
    updateTextColor()
  }

  /**
   * Set whether the button is enabled. Note that the content position is updated. The text color is
   * set to the passed in color.
   */
  fun setEnabledWithColor(enabled: Boolean, textColor: Int) {
    // updateContentPosition will cause a redraw, so exit early.
    if (this.isEnabled == enabled) {
      return
    }
    super.setEnabled(enabled)
    updateContentPosition()
    setTextColor(textColor)
  }

  override fun setPressed(pressed: Boolean) {
    // updateContentPosition will cause a redraw, so exit early.
    if (this.isPressed == pressed) {
      return
    }
    super.setPressed(pressed)
    updateContentPosition()
  }

  override fun setTypeface(tf: Typeface?) {
    super.setTypeface(duoTypeface(tf))
  }

  override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter)
    measureText()
  }

  /** Measures size of the current text, storing its bounds in textBounds. */
  private fun measureText() {
    // Initialize textBounds here, since text can change during parent init
    textBounds =
      (textBounds ?: Rect()).also {
        if (text == null) {
          it.set(0, 0, 0, 0)
        } else {
          val textString =
            when {
              isAllCapsInternal && isInEditMode -> text.toString().uppercase()
              isAllCapsInternal && !isInEditMode ->
                text.toString().uppercase(Locale./* splinter ignore */ getDefault())
              else -> text.toString()
            }
          paint.getTextBounds(textString, 0, textString.length, it)
        }
      }
  }

  /** Animates shine effect on this Button. */
  fun animateShine(duration: Long, onEnd: () -> Unit) {
    val shineDrawable = ShineDrawable(faceColor)

    updateBackground(faceDrawable = shineDrawable)

    ValueAnimator.ofFloat(0f, 1f)
      .apply {
        this.duration = duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
          shineDrawable.setAnimationStep(it.animatedValue as? Float ?: 0f)
          updateBackground(faceDrawable = shineDrawable)
        }
        addListener(
          onEnd = {
            updateBackground(faceDrawable = null)
            onEnd()
          }
        )
      }
      .start()
  }

  /**
   * Get auto advance indicator animation which lasts for 2 seconds, signifies when the button
   * triggers an auto-advance action.
   */
  fun getAutoAdvanceIndicatorAnimator(duration: Long): Animator? {
    val whiteGroup = resources.getIntArray(R.array.button_face_color_white_group)

    val (indicatorColor, alpha) =
      if (faceColor in whiteGroup) {
        currentTextColor to FACE_WHITE_INDICATOR_ALPHA
      } else {
        R.color.juicyWhite to FACE_COLORED_INDICATOR_ALPHA
      }

    val autoAdvanceIndicatorDrawable = AutoAdvanceIndicatorDrawable(indicatorColor, alpha)

    autoAdvanceIndicatorAnimator =
      ValueAnimator.ofFloat(0f, 1f).apply {
        this.duration = duration
        interpolator = DecelerateInterpolator()
        addUpdateListener { value ->
          autoAdvanceIndicatorDrawable?.let { drawable ->
            drawable.setAnimationStep(value.animatedValue as? Float ?: 0f)
            updateBackground(overlayDrawable = drawable)
          }
        }
        addListener(
          onEnd = {
            performClick()
            updateBackground(overlayDrawable = null)
          }
        )
      }
    return autoAdvanceIndicatorAnimator
  }

  /** Clear listeners and cancel auto advance indicator animation */
  fun clearAutoAdvanceAnimation() {
    autoAdvanceIndicatorAnimator?.removeAllListeners()
    autoAdvanceIndicatorAnimator?.cancel()
    updateBackground(overlayDrawable = null)
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    if (changed) {
      // Need to re-measure text otherwise onDraw() might use an outdated value for text width
      measureText()
    }
  }

  // When a side drawable is specified, we must shift the canvas pre-draw to center the button's
  // visible contents. This hack is necessary because the native solution, drawablePadding,
  // requires layout_width="wrap_content" - otherwise the drawable just ends up getting positioned
  // flush with the button edge. This can cause this view to be misaligned on RTL when
  // draw(canvas) is called.
  // https://stackoverflow.com/a/44776402
  // https://stackoverflow.com/a/6671544
  override fun onDraw(canvas: Canvas) {
    if (startDrawable != null || endDrawable != null || showProgress) {
      val textWidth = textBounds?.width() ?: 0
      val drawableWidth =
        if (showProgress) progressDrawable?.bounds?.width() ?: 0
        else (startDrawable?.bounds?.width() ?: 0) + (endDrawable?.bounds?.width() ?: 0)
      val interiorPadding = if (text.isNullOrEmpty()) 0 else compoundDrawablePadding
      val contentWidth = textWidth + drawableWidth + interiorPadding
      val unoccupiedWidth = measuredWidth - contentWidth - paddingStart - paddingEnd
      val translationX = unoccupiedWidth / 2f
      val isGravityLeft =
        Gravity.getAbsoluteGravity(gravity, layoutDirection) and Gravity.HORIZONTAL_GRAVITY_MASK ==
          Gravity.LEFT
      sideDrawableTranslation = if (isGravityLeft) translationX else -translationX
      canvas.translate(sideDrawableTranslation, 0f)
    }
    super.onDraw(canvas)
  }

  private companion object {
    private const val FACE_WHITE_INDICATOR_ALPHA = 25
    private const val FACE_COLORED_INDICATOR_ALPHA = 65
  }
}
