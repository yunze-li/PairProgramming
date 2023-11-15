package com.duolingo.app.sessionlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.duolingo.app.R
import com.duolingo.app.base.BaseActivity
import com.duolingo.app.databinding.ActivityLayoutToLoadFragmentBinding
import com.duolingo.app.extensions.addFragment
import com.duolingo.app.extensions.enableToolbar
import com.duolingo.app.extensions.getLongExtra
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId

class SessionListActivity : BaseActivity<ActivityLayoutToLoadFragmentBinding>() {

    private val courseId: Long by lazy { getLongExtra(EXTRA_COURSE_ID) }

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
        enableToolbar(true, "Current Course: $courseId")
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.container, SessionListFragment.newInstance(courseId))
        }
    }

    companion object {
        private const val EXTRA_COURSE_ID = "extra_course_id"

        fun newIntent(context: Context, courseId: LongId<Course>): Intent =
            Intent(context, SessionListActivity::class.java).apply {
                putExtra(EXTRA_COURSE_ID, courseId.get())
            }
    }
}
