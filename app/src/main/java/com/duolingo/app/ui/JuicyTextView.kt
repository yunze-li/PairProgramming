package com.duolingo.app.ui

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.text.LineBreakConfig
import android.os.Build
import android.text.Spanned
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.doOnLayout
import androidx.fragment.app.FragmentActivity
import com.duolingo.app.R
import com.duolingo.app.extensions.containsView
import com.duolingo.app.extensions.duoTypeface
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.roundToInt

/** TextView that uses our typeface. */
@AndroidEntryPoint
open class JuicyTextView(
  context: Context,
  attrs: AttributeSet?,
  defStyle: Int,
) : AppCompatTextView(context, attrs, defStyle) {

  constructor(context: Context) : this(context, null, 0)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  private var trimViewSize: Boolean = false
  private var originalTextSize: Float
  private val minTextSize: Int
  private var capRadius: Float = 0f
  private var strokeWidth: Float = 0f
  private var strokeColor: Int = 0x000

  init {
    context.withStyledAttributes(attrs, R.styleable.JuicyTextView, defStyle, 0) {
      trimViewSize = getBoolean(R.styleable.JuicyTextView_trimViewSize, trimViewSize)

      capRadius = getDimensionPixelSize(R.styleable.JuicyTextView_capRadius, 0).toFloat()
      strokeWidth = getDimensionPixelSize(R.styleable.JuicyTextView_strokeWidth, 0).toFloat()
      strokeColor = getColor(R.styleable.JuicyTextView_strokeColor, 0x000)

      // If a cap radius is provided, round the background's two top corners
      val initialBackground = background
      if (initialBackground is ColorDrawable) {
        setSolidColorBackground(initialBackground.color)
      }
    }
    minTextSize = context.resources.getDimensionPixelSize(R.dimen.juicyAutoSizeMinTextSize)

    // Initializing original text side for cases where the view is created without inflation
    originalTextSize = textSize

    // This will prevent Hindi and Arabic characters from being clipped
    includeFontPadding = false
  }

  private fun setSolidColorBackground(color: Int) {
    if (capRadius > 0) {
      val radii = floatArrayOf(capRadius, capRadius, capRadius, capRadius, 0f, 0f, 0f, 0f)
      background = ShapeDrawable(RoundRectShape(radii, null, null)).apply { paint.color = color }
    } else {
      super.setBackgroundColor(color)
    }
  }

  override fun setBackgroundColor(color: Int) {
    setSolidColorBackground(color)
  }

  /** Function for setting text stroke color. */
  fun setStrokeColor(color: Int) {
    strokeColor = color
  }

  /** Function for setting text stroke color. */
  fun setStrokeColor(color: UiModel<Color>) {
    setStrokeColor(color.resolve(context).colorInt)
  }

  override fun onFinishInflate() {
    super.onFinishInflate()

    // Overwrite originalTextSize if view is created with inflation.
    originalTextSize = textSize
  }

  override fun setTypeface(tf: Typeface?) {
    super.setTypeface(duoTypeface(tf))
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    /**
     * This is adjusting the view size (width and height) to take only necessary space from the
     * parent available space. This is useful when showing multi-line text in a TextView with
     * width=WRAP_CONTENT, in this case the view is taking whole horizontal space i.e. behaving like
     * width=MATCH_PARENT.
     */
    if (trimViewSize &&
        MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY &&
        layout != null &&
        layout.lineCount > 1
    ) {

      // Find the widest line.
      val widestLine =
        (0 until layout.lineCount)
          .maxOf { layout.getLineWidth(it) }
          // Make sure we round up. If we round down, we may not give enough width to the view and
          // cause
          // more line breaks than necessary.
          .let { ceil(it).roundToInt() }

      // If the width based on the widest line is smaller, remeasure with the smaller width.
      val newWidth = widestLine + totalPaddingLeft + totalPaddingRight
      if (newWidth < measuredWidth) {
        val newWidthSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.AT_MOST)
        super.onMeasure(newWidthSpec, heightMeasureSpec)
      }
    }

    // Increase height to avoid cutting off the dotted underline for hints and solid underline for
    // hide ranges
    if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
      return
    }
//    val spanned = text as? Spanned ?: return
//    val extraHeight =
//      spanned.getSpans(0, spanned.length, ExtraHeightSpan::class.java).maxOfOrNull {
//        it.extraHeight
//      }
//        ?: return
//    setMeasuredDimension(measuredWidth, measuredHeight + extraHeight.roundToInt())
    setMeasuredDimension(measuredWidth, measuredHeight)
  }

  /** Function for increasing text size in languages where we want larger text. */
  fun increaseTextSize(sizeInSp: Float = DEFAULT_LARGE_TEXT_SIZE) {
    textSize = sizeInSp
  }

  override fun onDraw(canvas: Canvas) {
    if (strokeWidth > 0) {
      // save the text color
      val currentTextColor = currentTextColor
      // set paint to stroke mode and specify
      // stroke color and width
      paint.style = Paint.Style.STROKE
      paint.strokeWidth = strokeWidth
      setTextColor(strokeColor)
      // draw text stroke
      super.onDraw(canvas)
      // revert the color back to the one
      // initially specified
      setTextColor(currentTextColor)
      // set paint to fill mode
      paint.style = Paint.Style.FILL
      // draw the fill part of text
      super.onDraw(canvas)
    } else {
      super.onDraw(canvas)
    }
  }

  private fun getActivity(): String? = (context as? Activity)?.localClassName

  private fun getFragment(): String? =
    (context as? FragmentActivity)?.supportFragmentManager?.let {
      for (fragment in it.fragments) {
        if (fragment.view?.containsView(this) == true) {
          return fragment.tag
        }
      }
      return null
    }

  companion object {
    private const val DEFAULT_LARGE_TEXT_SIZE = 26f
  }
}
