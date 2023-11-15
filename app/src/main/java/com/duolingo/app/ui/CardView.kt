package com.duolingo.app.ui

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.withSave
import androidx.core.view.updatePaddingRelative
import com.duolingo.app.R
import com.duolingo.app.extensions.getDescendantViews

/** A card with a bottom "lip" that disappears when pressed down, creating a 3D effect. */
open class CardView(
  context: Context,
  attrs: AttributeSet?,
  defStyle: Int,
) : LinearLayout(context, attrs, defStyle), LipView {

  constructor(context: Context) : this(context, null, 0)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  final override var internalPaddingTop: Int = paddingTop
    private set
  final override var internalPaddingBottom: Int = paddingBottom
    private set
  final override var borderWidth: Int = 0
    private set
  final override var cornerRadius: Int = 0
    private set
  final override var dimWhenDisabled: Boolean = false
    private set
  final override var faceColor: Int = 0
    private set
  final override var lipColor: Int = 0
    private set
  final override var faceDrawable: Drawable? = null
    private set
  final override var lipDrawable: Drawable? = null
    private set
  final override var disabledFaceColor: Int =
    ContextCompat.getColor(context, R.color.juicySwan)
    private set
  final override var lipHeight: Int = 0
    private set
  final override var position: LipView.Position = LipView.Position.NONE
    private set
  final override var shouldStyleDisabledState: Boolean = false
    private set
  final override var pressedProgress: Float? = null
    private set
  final override var transitionalInnerBackground: LipView.TransitionalInnerBackground? = null
    private set

  private var selectable: Boolean = false
  private var cardCapView: View? = null

  private var selectedFaceColor: Int = ContextCompat.getColor(context, R.color.juicyIguana)
  private var selectedLipColor: Int = ContextCompat.getColor(context, R.color.juicyBlueJay)
  private var selectedTextColor: Int = ContextCompat.getColor(context, R.color.juicyMacaw)
  private var unselectedTextColor: Int = ContextCompat.getColor(context, R.color.juicyEel)
  private var selectedTransliterationColor: Int =
    ContextCompat.getColor(context, R.color.juicyMacaw)
  private var unselectedTransliterationColor: Int =
    ContextCompat.getColor(context, R.color.juicyHare)
  private var selectedBorderWidth: Int = borderWidth
  var borderColor: Int = 0
    private set
  private var selectedBorderColor: Int = 0

  private val borderPaint = Paint().apply { isAntiAlias = true }
  @Suppress("UNCHECKED_CAST")
  protected val argbEvaluator: TypeEvaluator<Int> = ArgbEvaluator() as TypeEvaluator<Int>
  protected val lipColorProperty: Property<CardView, Int> =
    object : Property<CardView, Int>(Int::class.java, "") {
      override fun get(obj: CardView): Int? = obj.selectableLipColor()

      override fun set(obj: CardView, value: Int) {
        obj.updateBackground(lipColor = value)
      }
    }
  private val faceColorProperty: Property<CardView, Int> =
    object : Property<CardView, Int>(Int::class.java, "") {
      override fun get(obj: CardView): Int? = obj.selectableFaceColor()

      override fun set(obj: CardView, value: Int) {
        obj.updateBackground(faceColor = value)
      }
    }
  protected val backgroundColorProperty: Property<CardView, Int> =
    object : Property<CardView, Int>(Int::class.java, "") {
      override fun get(obj: CardView): Int? = obj.selectableLipColor()

      override fun set(obj: CardView, value: Int) {
        obj.updateBackground(
          lipColor = value,
          faceColor = value,
        )
      }
    }

  private var pressAnimatorAdded = false
  private var animatePress = false

  init {
    // Get custom attributes
    attrs ?: throw RuntimeException("Null attributes")

    context.withStyledAttributes(attrs, R.styleable.LipView, defStyle, 0) {
      borderWidth = getDimensionPixelSize(R.styleable.LipView_borderWidth, borderWidth)
      cornerRadius = getDimensionPixelSize(R.styleable.LipView_cornerRadius, cornerRadius)
      dimWhenDisabled = getBoolean(R.styleable.LipView_dimWhenDisabled, dimWhenDisabled)
      disabledFaceColor = getColor(R.styleable.LipView_disabledFaceColor, disabledFaceColor)
      faceColor = getColor(R.styleable.LipView_faceColor, faceColor)
      lipColor = getColor(R.styleable.LipView_lipColor, lipColor)
      faceDrawable = getDrawable(R.styleable.LipView_faceDrawable)
      lipDrawable = getDrawable(R.styleable.LipView_lipDrawable)
      lipHeight = maxOf(getDimensionPixelSize(R.styleable.LipView_lipHeight, 0), borderWidth)
      position = LipView.Position.valueOf(getInt(R.styleable.LipView_position, -1))
      selectable = getBoolean(R.styleable.LipView_selectable, selectable)
      shouldStyleDisabledState =
        getBoolean(R.styleable.LipView_styleDisabledState, shouldStyleDisabledState)
    }

    context.withStyledAttributes(attrs, R.styleable.CardView, defStyle, 0) {
      isEnabled = getBoolean(R.styleable.CardView_android_enabled, isEnabled)
      selectedFaceColor = getColor(R.styleable.CardView_selectedFaceColor, selectedFaceColor)
      selectedLipColor = getColor(R.styleable.CardView_selectedLipColor, selectedLipColor)
      selectedTextColor = getColor(R.styleable.CardView_selectedTextColor, selectedTextColor)
      unselectedTextColor =
        getColor(R.styleable.CardView_unselectedTextColor, unselectedTextColor)
      borderColor = getColor(R.styleable.CardView_borderColor, lipColor)
      selectedBorderColor =
        getColor(R.styleable.CardView_selectedBorderColor, selectedLipColor)
      selectedTransliterationColor =
        getColor(
          R.styleable.CardView_selectedTransliterationColor,
          selectedTransliterationColor
        )
      unselectedTransliterationColor =
        getColor(
          R.styleable.CardView_unselectedTransliterationColor,
          unselectedTransliterationColor
        )
      selectedBorderWidth =
        getDimensionPixelSize(R.styleable.CardView_selectedBorderWidth, borderWidth)
    }

    drawBackground()

    if (selectable) {
      setOnClickListener { isSelected = !isSelected }
    }
  }

  /** Updates background attributes and redraws the background */
  fun updateBackground(
    internalPaddingTop: Int = this.internalPaddingTop,
    internalPaddingBottom: Int = this.internalPaddingBottom,
    borderWidth: Int = this.borderWidth,
    @ColorInt faceColor: Int = this.faceColor,
    @ColorInt lipColor: Int = this.lipColor,
    @ColorInt borderColor: Int = this.borderColor,
    lipHeight: Int = this.lipHeight,
    cornerRadius: Int = this.cornerRadius,
    position: LipView.Position = this.position,
    shouldStyleDisabledState: Boolean = this.shouldStyleDisabledState,
    faceDrawable: Drawable? = this.faceDrawable,
    lipDrawable: Drawable? = this.lipDrawable,
    pressedProgress: Float? = this.pressedProgress,
    transitionalInnerBackground: LipView.TransitionalInnerBackground? =
      this.transitionalInnerBackground,
    @ColorInt selectedFaceColor: Int = this.selectedFaceColor,
  ) {
    this.internalPaddingTop = internalPaddingTop
    this.internalPaddingBottom = internalPaddingBottom
    this.borderWidth = borderWidth
    this.faceColor = faceColor
    this.lipColor = lipColor
    this.borderColor = borderColor
    this.lipHeight = lipHeight
    this.cornerRadius = cornerRadius
    this.position = position
    this.shouldStyleDisabledState = shouldStyleDisabledState
    this.pressedProgress = pressedProgress
    this.transitionalInnerBackground = transitionalInnerBackground
    this.selectedFaceColor = selectedFaceColor
    drawBackground(
      selectableFaceColor(),
      selectableLipColor(),
      selectableBorderWidth(),
      faceDrawable = faceDrawable,
      lipDrawable = lipDrawable,
    )
    invalidate()
  }

  // Override drawBackground() as final to avoid warning
  final override fun drawBackground(
    @ColorInt faceColor: Int,
    @ColorInt lipColor: Int,
    borderWidth: Int,
    @ColorInt disabledFaceColor: Int,
    faceDrawable: Drawable?,
    lipDrawable: Drawable?,
    transitionalInnerBackground: LipView.TransitionalInnerBackground?,
    overlayDrawable: Drawable?,
  ) {
    super.drawBackground(
      faceColor,
      lipColor,
      borderWidth,
      disabledFaceColor,
      faceDrawable,
      lipDrawable,
      transitionalInnerBackground,
      overlayDrawable,
    )
  }

  /** Smoothly animates the face down and up when it's pressed */
  fun animatePress() {
    animatePress = true
  }

  /** Helper method that draws border only. */
  private fun drawBorder(canvas: Canvas, borderColor: Int, borderWidth: Int) {
    borderPaint.apply {
      strokeWidth = borderWidth.toFloat()
      color = borderColor
    }

    canvas.drawPath(getBorderPath(), borderPaint)
  }

  /** Helper method that constructs rounded rectangle path based on the given arguments. */
  protected fun roundedRectPath(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
    topLeftCornerRadius: Float,
    topRightCornerRadius: Float,
    bottomRightCornerRadius: Float,
    bottomLeftCornerRadius: Float
  ): Path =
    Path().apply {
      val width = right - left
      val height = bottom - top

      // Start drawing the path. First move to start position - below right corner.
      moveTo(right, top + topRightCornerRadius)

      // Top right corner
      arcTo(
        right - 2 * topRightCornerRadius,
        top,
        right,
        top + 2 * topRightCornerRadius,
        0f,
        -90f,
        false
      )

      rLineTo(-(width - topLeftCornerRadius - topRightCornerRadius), 0f)

      // Top left corner
      arcTo(
        left,
        top,
        left + 2 * topLeftCornerRadius,
        top + 2 * topLeftCornerRadius,
        270f,
        -90f,
        false
      )
      rLineTo(0f, height - topLeftCornerRadius - bottomLeftCornerRadius)

      // Bottom left corner
      arcTo(
        left,
        bottom - 2 * bottomLeftCornerRadius,
        left + 2 * bottomLeftCornerRadius,
        bottom,
        180f,
        -90f,
        false
      )
      rLineTo(width - bottomLeftCornerRadius - bottomRightCornerRadius, 0f)

      // Bottom right corner
      arcTo(
        right - 2 * bottomRightCornerRadius,
        bottom - 2 * bottomRightCornerRadius,
        right,
        bottom,
        90f,
        -90f,
        false
      )
      rLineTo(0f, -(height - bottomRightCornerRadius - topRightCornerRadius))

      close()
    }

  /** Helper method that returns path that can be used to draw CardView border only. */
  private fun getBorderPath(): Path {
    /** Get outer path of the CardView border. */
    val pressedProgress = pressedProgress ?: if (isPressed) 1F else 0F
    val topOffset = (lipHeight - borderWidth) * pressedProgress
    val bottomOffset = lipHeight - (lipHeight - borderWidth) * pressedProgress

    val outerPath =
      roundedRectPath(
        0f,
        topOffset,
        width.toFloat(),
        height.toFloat(),
        position.getOuterRadii(cornerRadius)[0],
        position.getOuterRadii(cornerRadius)[2],
        position.getOuterRadii(cornerRadius)[4],
        position.getOuterRadii(cornerRadius)[6]
      )

    val insetRect =
      position.getInsetRect(borderWidth, borderWidth, borderWidth, bottomOffset.toInt())

    /** Get inner path of the CardView border (outer path - borderWidth). */
    val innerPath =
      roundedRectPath(
        insetRect.left.toFloat(),
        topOffset + insetRect.top,
        (width - insetRect.right).toFloat(),
        (height - insetRect.bottom).toFloat(),
        position.getOuterRadii(cornerRadius)[0] - borderWidth,
        position.getOuterRadii(cornerRadius)[2] - borderWidth,
        position.getOuterRadii(cornerRadius)[4] - borderWidth,
        position.getOuterRadii(cornerRadius)[6] - borderWidth
      )

    // Subtract innerPath from outerPath to get the path for the border.
    outerPath.op(innerPath, Path.Op.DIFFERENCE)

    return outerPath
  }

  override fun drawChild(canvas: Canvas, child: View?, drawingTime: Long): Boolean {
    var result = false

    canvas.withSave {
      if (clipChildren) {
        // If clip children is set make sure they are properly clipped taking rounded corners
        // and border into account.

        val halfBorderWidth = borderWidth / 2
        val pressedProgress = pressedProgress ?: if (isPressed) 1F else 0F
        val topOffset = (lipHeight - borderWidth) * pressedProgress
        val bottomOffset = lipHeight - halfBorderWidth + (borderWidth - lipHeight) * pressedProgress

        // Clip children on the middle of the border.
        clipPath(
          roundedRectPath(
            halfBorderWidth.toFloat(),
            topOffset + halfBorderWidth,
            (this@CardView.width - halfBorderWidth).toFloat(),
            this@CardView.height - bottomOffset,
            position.getOuterRadii(cornerRadius)[0] - halfBorderWidth,
            position.getOuterRadii(cornerRadius)[2] - halfBorderWidth,
            position.getOuterRadii(cornerRadius)[4] - halfBorderWidth,
            position.getOuterRadii(cornerRadius)[6] - halfBorderWidth
          ),
        )
      }

      result = super.drawChild(canvas, child, drawingTime)
    }

    return result
  }

  override fun onDrawForeground(canvas: Canvas) {
    if (clipChildren) {
      // Draw border again on top of everything to make sure it's blended properly with clipped
      // children. Clipping children leaves jagged edges because anti aliasing isn't supported
      // with clipping.
      drawBorder(canvas, selectableBorderColor(), selectableBorderWidth())
    } else {
      super.onDrawForeground(canvas)
    }
  }

  /**
   * Updates the padding for this CardView dynamically. Prefer this method over
   * [updatePaddingRelative].
   */
  fun updateInternalPadding(start: Int, top: Int, end: Int, bottom: Int) {
    // Update start and end padding
    updatePaddingRelative(start, 0, end, 0)
    // Update top and bottom padding - updateContentPosition will use vertical padding to push
    // content down on press so save true padding here.
    internalPaddingTop = top
    internalPaddingBottom = bottom
    // Redraw with updated padding values
    updateContentPosition()
  }

  override fun setEnabled(enabled: Boolean) {
    // updateContentPosition will cause a redraw, so exit early.
    if (this.isEnabled == enabled) {
      return
    }
    super.setEnabled(enabled)
    isClickable = enabled
    updateContentPosition()
  }

  override fun setPressed(pressed: Boolean) {
    // updateContentPosition will cause a redraw, so exit early.
    if (this.isPressed == pressed) {
      return
    }
    if (animatePress && !pressAnimatorAdded) {
      addPressAnimator { updateBackground(pressedProgress = it) }
      pressAnimatorAdded = true
    }
    super.setPressed(pressed)
    updateContentPosition()

    invalidate()
  }

  override fun setSelected(selected: Boolean) {
    super.setSelected(selected)

    if (selectable) {
      drawBackground(selectableFaceColor(), selectableLipColor(), selectableBorderWidth())
      forEachDescendantJuicyTextView {
        it.setTextColor(selectableTextColor())
      }
    }
  }

  /** Sets the lip color for this CardView. */
  fun setLipColor(@ColorInt lipColor: Int) {
    this.lipColor = lipColor
    invalidate()
  }

  /** Sets the lip color for this CardView. */
  fun setLipColor(lipColor: UiModel<Color>) {
    this.lipColor = lipColor.resolve(context).colorInt
    invalidate()
  }

  /** Get border color change animator for this CardView in unselected state */
  fun getLipColorChangeAnimator(
    toColorRes: UiModel<Color>,
    fromColorRes: UiModel<Color>? = null,
    duration: Long? = null,
    startDelay: Long? = null,
  ): Animator =
    ObjectAnimator.ofObject(
        this,
        lipColorProperty,
        argbEvaluator,
        fromColorRes?.resolve(context)?.colorInt ?: selectableLipColor(),
        toColorRes.resolve(context).colorInt
      )
      .apply {
        startDelay?.let { this.startDelay = it }
        duration?.let { this.duration = it }
      }

  /** Get face color change animator for this CardView in unselected state. */
  fun getFaceColorChangeAnimator(
    toColorRes: UiModel<Color>,
    fromColorRes: UiModel<Color>? = null,
    duration: Long? = null,
    startDelay: Long? = null,
  ): Animator =
    ObjectAnimator.ofObject(
        this,
        faceColorProperty,
        argbEvaluator,
        fromColorRes?.resolve(context)?.colorInt ?: selectableFaceColor(),
        toColorRes.resolve(context).colorInt
      )
      .apply {
        startDelay?.let { this.startDelay = it }
        duration?.let { this.duration = it }
      }

  /** Get lip and face color change animator, which changes both of them to same color */
  fun getLipAndFaceColorChangeAnimator(
    toColorRes: UiModel<Color>,
    fromColorRes: UiModel<Color>? = null,
    duration: Long? = null,
    startDelay: Long? = null,
  ): Animator =
    ObjectAnimator.ofObject(
        this,
        backgroundColorProperty,
        argbEvaluator,
        fromColorRes?.resolve(context)?.colorInt ?: selectableLipColor(),
        toColorRes.resolve(context).colorInt
      )
      .apply {
        startDelay?.let { this.startDelay = it }
        duration?.let { this.duration = it }
      }

  protected fun selectableLipColor(): Int = if (isSelected) selectedLipColor else lipColor
  private fun selectableBorderColor(): Int = if (isSelected) selectedBorderColor else borderColor
  protected fun selectableFaceColor(): Int = if (isSelected) selectedFaceColor else faceColor
  protected fun selectableTextColor(): Int =
    if (isSelected) selectedTextColor else unselectedTextColor
  protected fun selectableTransliterationTextColor(): Int =
    if (isSelected) selectedTransliterationColor else unselectedTransliterationColor
  private fun selectableBorderWidth() = if (isSelected) selectedBorderWidth else borderWidth

  private fun forEachDescendantJuicyTextView(f: (JuicyTextView) -> Unit) =
    getDescendantViews().filter { it != cardCapView }.forEach { (it as? JuicyTextView)?.apply(f) }
}
