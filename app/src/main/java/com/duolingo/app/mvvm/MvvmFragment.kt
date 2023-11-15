package com.duolingo.app.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.duolingo.app.DuoApplication
import com.duolingo.app.base.BaseActivity
import com.duolingo.app.di.components.ActivityComponent
import com.duolingo.app.di.components.ApplicationComponent
import javax.inject.Inject

/**
 * Base fragment for all of our [Fragment] classes.
 *
 * Usage:
 * ```
 * class SomeFragment : MvvmFragment<SomeBinding>(SomeBinding::inflate) {
 *   override fun onViewCreated(binding: SomeBinding, savedInstanceState: Bundle?) {
 *     viewModel.apply {
 *       whileStarted(someText) { binding.someTextView = it }
 *       whileStarted(someOtherText) { binding.someOtherTextView = it }
 *     }
 *   }
 * }
 * ```
 */
abstract class MvvmFragment<VB : ViewBinding>(
  private val bindingInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment(), MvvmView {

  /**
   * Inject the ApplicationComponent and ActivityComponent
   */
//  protected val appComponent: ApplicationComponent by lazy { (requireActivity().application as DuoApplication).appComponent }
  protected val activityComponent: ActivityComponent by lazy { (activity as BaseActivity<*>).activityComponent }

  @Inject lateinit var baseMvvmViewDependenciesFactory: MvvmView.Dependencies.Factory

  override val mvvmDependencies: MvvmView.Dependencies by lazy {
    baseMvvmViewDependenciesFactory.create(
      uiLifecycleOwnerProvider = { viewLifecycleOwner },
    )
  }

  private var internalBinding: VB? = null

  /**
   * Identical to the standard [onViewCreated], except that this passes the already inflated view
   * binding for subclasses to bind to their View Models' streams via [whileStarted]. It is an error
   * to maintain a reference to the given [binding] inside subclasses.
   *
   * @param binding View binding for this Fragment, not yet attached to the Fragment's parent.
   * @param savedInstanceState If non-null, this fragment is being re-constructed from a previously
   * saved state.
   */
  protected abstract fun onViewCreated(binding: VB, savedInstanceState: Bundle?)

  /**
   * Callback from [onDestroyView] which allows subclasses to perform any necessary view cleanup. It
   * is an error to maintain a reference to the given [binding] inside subclasses.
   *
   * @param binding View binding for this Fragment, about to be destroyed.
   */
  protected open fun onViewDestroyed(binding: VB) {}

  /**
   * Managed as described on Android Developers:
   * https://developer.android.com/topic/libraries/view-binding#fragments
   */
  final override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = bindingInflate.invoke(inflater, container, false).also { internalBinding = it }.root

  final override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit =
    onViewCreated(
      binding =
        checkNotNull(internalBinding) {
          """
          View binding is impossibly unavailable in onViewCreated.
          The current lifecycle state is ${viewLifecycleOwner.lifecycle.currentState}.
          """.trimIndent()
        },
      savedInstanceState = savedInstanceState,
    )

  final override fun onDestroyView() {
    onViewDestroyed(
      binding =
        checkNotNull(internalBinding) {
          """
          View binding is impossibly unavailable in onDestroyView.
          The current lifecycle state is ${viewLifecycleOwner.lifecycle.currentState}.
          """.trimIndent()
        }
    )
    internalBinding = null
    super.onDestroyView()
  }
}
