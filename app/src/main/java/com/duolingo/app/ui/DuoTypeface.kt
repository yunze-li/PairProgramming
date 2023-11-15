package com.duolingo.app.ui

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.duolingo.app.R

/** Typeface utils. */
object DuoTypeface {

  /**
   * Gets the default typeface at regular weight.
   *
   * We should keep this method as fast as possible since slowness here can cause slow frames and
   * even crashes. In an API 29 AVD, [ResourcesCompat.getFont] takes ~2.65ms as measured by
   * [measureNanoTime] while [ResourcesCompat.getCachedFont] takes only ~0.0343ms on a cache hit.
   * This is pretty significant given that each frame gets only 16ms to achieve 60fps. We suspect
   * that slowness here is also the real cause of the crash "Resources$NotFoundException: Font
   * resource ID #0x7f090001 could not be retrieved", which first appeared in March 2021 in the app
   * version (5.1.5) that introduced a change in which we removed our homemade font cache.
   *
   * https://console.firebase.google.com/u/0/project/duolingo.com:fleet-diagram-694/crashlytics/app/android:com.duolingo/issues/adef160359e3448551c0d0f8b9a557eb
   * https://github.com/duolingo/duolingo-android/pull/11808
   * https://github.com/duolingo/duolingo-android/pull/19331
   */
  fun getDefaultTypeface(context: Context): Typeface =
    checkNotNull(
      ResourcesCompat.getCachedFont(context, R.font.din_regular)
        ?: ResourcesCompat.getFont(context, R.font.din_regular)
    )

  /** Gets the default typeface at bold weight. */
  fun getDefaultBoldTypeface(context: Context): Typeface =
    checkNotNull(
      ResourcesCompat.getCachedFont(context, R.font.din_bold)
        ?: ResourcesCompat.getFont(context, R.font.din_bold)
    )
}
