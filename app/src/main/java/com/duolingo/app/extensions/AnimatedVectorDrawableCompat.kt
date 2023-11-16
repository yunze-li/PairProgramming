package com.duolingo.app.extensions

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.View
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import java.lang.ref.WeakReference

/** Loops an [AnimatedVectorDrawableCompat] forever. */
fun AnimatedVectorDrawableCompat.loopForever(containingView: View): Unit =
  registerAnimationCallback(LoopingAnimationCallback(this, containingView))

private class LoopingAnimationCallback(
  animatedDrawable: AnimatedVectorDrawableCompat,
  containingView: View,
) : Animatable2Compat.AnimationCallback() {

  private val animatedDrawableRef = WeakReference(animatedDrawable)
  private val containingViewRef = WeakReference(containingView)

  override fun onAnimationEnd(drawable: Drawable?) = ensureValidState { _, containingView ->
    // Post to ensure UI thread for backwards API compatibility
    containingView.post {
      ensureValidState { animatedDrawable, _ ->
        try {
          animatedDrawable.start()
        } catch (_: NullPointerException) {
          // Some older Samsung platforms NPE here even with the above checks
          // in place. This should only happen on view destruction, so we're
          // safe to ignore any failures here.
        }
      }
    }
  }

  private fun ensureValidState(callback: (AnimatedVectorDrawableCompat, View) -> Unit) {
    val animatedDrawable = animatedDrawableRef.get()
    val containingView = containingViewRef.get()

    // This condition was initially added to support Espresso tests. Espresso tests disable
    // animations, which cause them to skip immediately to the end. The listener would then
    // post a callback to restart the animation and this would happen in an infinite loop. This
    // is not a problem from a UX perspective, but tests that show an element that loops forever
    // were timing out because they were never idle because the animation was constantly
    // starting and stopping. This check also makes sense from a battery usage perspective
    // because constantly restarting an animation that doesn't animate is a lot of unnecessary
    // work.
    val animatorsEnabled = ValueAnimator.areAnimatorsEnabled()

    if (animatedDrawable != null &&
        containingView != null &&
        animatorsEnabled &&
        containingView.isAttachedToWindow
    ) {
      callback.invoke(animatedDrawable, containingView)
    }
  }
}
