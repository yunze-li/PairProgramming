package com.duolingo.app.extensions

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.duolingo.app.R

/** Convenience wrapper around [viewModels] for assisted injection. */
inline fun <reified VM : ViewModel> AppCompatActivity.assistedViewModels(
  noinline assistedCreate: () -> VM,
): Lazy<VM> =
  viewModels(
    factoryProducer = {
      object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = assistedCreate.invoke() as T
      }
    }
  )

/** Convenience wrapper around [viewModels] needing a [SavedStateHandle] for assisted injection. */
inline fun <reified VM : ViewModel> AppCompatActivity.assistedViewModelsWithHandle(
  noinline assistedCreate: (SavedStateHandle) -> VM,
): Lazy<VM> =
  viewModels(
    factoryProducer = {
      /**
       * Implementation heavily inspired by the flows in underlying Hilt code:
       * [dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories]
       */
      object :
        AbstractSavedStateViewModelFactory(this@assistedViewModelsWithHandle, intent?.extras) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
          key: String,
          modelClass: Class<T>,
          handle: SavedStateHandle
        ): T = assistedCreate(handle) as T
      }
    }
  )

/**
 * Adds a [Fragment] to this activity's layout.
 * @param containerViewId The container view to where add the fragment.
 * @param fragment The fragment to be added.
 */
fun AppCompatActivity.addFragment(containerViewId: Int, fragment: Fragment) {
  supportFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
}

fun AppCompatActivity.enableToolbar(enableBack: Boolean = false, title: String? = null) {
  setSupportActionBar(findViewById(R.id.toolbar))
  title?.also { supportActionBar?.title = title }
  if (enableBack) {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }
}

fun AppCompatActivity.getLongExtra(key: String): Long = intent!!.extras!!.getLong(key)

fun AppCompatActivity.getBooleanExtra(key: String): Boolean = intent!!.extras!!.getBoolean(key)

fun AppCompatActivity.getStringExtra(key: String): String = intent!!.extras!!.getString(key)!!
