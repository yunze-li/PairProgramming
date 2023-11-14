package com.duolingo.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.duolingo.app.DuoApplication
import com.duolingo.app.di.components.ActivityComponent
import com.duolingo.app.di.components.ApplicationComponent

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private val applicationComponent: ApplicationComponent by lazy {
        (application as DuoApplication).appComponent
    }

    val activityComponent: ActivityComponent by lazy {
        applicationComponent.activityComponent().create(this)
    }

    private var _binding: VB? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle toolbar back arrow click here
        if (item.itemId == android.R.id.home) {
           finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}