package com.duolingo.app.extensions

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import java.io.Serializable

/** Convenience wrapper around [activityViewModels] for assisted injection. */
inline fun <reified VM : ViewModel> Fragment.assistedActivityViewModels(
  noinline assistedCreate: () -> VM,
): Lazy<VM> =
  activityViewModels(
    factoryProducer = {
      object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = assistedCreate.invoke() as T
      }
    }
  )

/**
 * Convenience wrapper around [activityViewModels] needing a [SavedStateHandle] for assisted
 * injection.
 */
inline fun <reified VM : ViewModel> Fragment.assistedActivityViewModelsWithHandle(
  noinline assistedCreate: (SavedStateHandle) -> VM,
): Lazy<VM> =
  activityViewModels(
    factoryProducer = {
      /**
       * TODO(DLAA-8839): Revert to simple Factory once Hilt supports SavedStateHandle
       * https://github.com/google/dagger/issues/2287
       *
       * Implementation heavily inspired by the flows in underlying Hilt code:
       * [dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories]
       */
      object : AbstractSavedStateViewModelFactory(requireActivity(), arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
          key: String,
          modelClass: Class<T>,
          handle: SavedStateHandle
        ): T = assistedCreate(handle) as T
      }
    }
  )

/** Convenience wrapper around [viewModels] for assisted injection. */
inline fun <reified VM : ViewModel> Fragment.assistedViewModels(
  noinline ownerProducer: () -> ViewModelStoreOwner = { this },
  noinline assistedCreate: () -> VM,
): Lazy<VM> =
  viewModels(
    ownerProducer = ownerProducer,
    factoryProducer = {
      object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = assistedCreate.invoke() as T
      }
    }
  )

/** Convenience wrapper around [viewModels] needing a [SavedStateHandle] for assisted injection. */
inline fun <reified VM : ViewModel> Fragment.assistedViewModelsWithHandle(
  noinline ownerProducer: () -> ViewModelStoreOwner = { this },
  noinline assistedCreate: (SavedStateHandle) -> VM,
): Lazy<VM> =
  viewModels(
    ownerProducer = ownerProducer,
    factoryProducer = {
      /**
       * TODO(DLAA-8839): Revert to simple Factory once Hilt supports SavedStateHandle
       * https://github.com/google/dagger/issues/2287
       *
       * Implementation heavily inspired by the flows in underlying Hilt code:
       * [dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories]
       */
      object : AbstractSavedStateViewModelFactory(this@assistedViewModelsWithHandle, arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
          key: String,
          modelClass: Class<T>,
          handle: SavedStateHandle
        ): T = assistedCreate(handle) as T
      }
    }
  )

inline fun <T : Fragment> T.build(args: Bundle.() -> Unit): T =
  apply { arguments = Bundle().apply(args) }

fun Fragment.getLongArg(key: String): Long = arguments!!.getLong(key)

fun Fragment.getBooleanArg(key: String): Boolean = arguments!!.getBoolean(key)

fun Fragment.getStringArg(key: String): String = arguments!!.getString(key)!!

fun <T : View?> Fragment.findViewById(@IdRes id: Int): T? = view?.findViewById<T>(id)
