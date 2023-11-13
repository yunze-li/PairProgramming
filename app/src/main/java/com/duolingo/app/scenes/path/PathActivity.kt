package com.duolingo.app.scenes.path

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.duolingo.app.R
import com.duolingo.app.databinding.ActivityLayoutToLoadFragmentBinding
import com.duolingo.app.extensions.addFragment
import com.duolingo.app.extensions.enableToolbar
import com.duolingo.app.scenes.base.view.ABaseActivity

class PathActivity : ABaseActivity<ActivityLayoutToLoadFragmentBinding>() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, PathActivity::class.java)
    }

    // View Binding
    override val bindingInflater: (LayoutInflater) -> ActivityLayoutToLoadFragmentBinding =
        ActivityLayoutToLoadFragmentBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.Base_Theme)
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        enableToolbar()
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.container, PathFragment.newInstance())
        }
    }
}
