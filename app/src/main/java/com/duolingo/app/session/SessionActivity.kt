package com.duolingo.app.session

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.duolingo.app.R
import com.duolingo.app.base.BaseActivity
import com.duolingo.app.databinding.ActivityLayoutToLoadFragmentBinding
import com.duolingo.app.extensions.addFragment
import com.duolingo.app.extensions.enableToolbar
import com.duolingo.app.extensions.getLongExtra
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import io.reactivex.rxjava3.subjects.PublishSubject

class SessionActivity : BaseActivity<ActivityLayoutToLoadFragmentBinding>() {

    private val courseId: Long by lazy { getLongExtra(EXTRA_COURSE_ID) }

    override val bindingInflater: (LayoutInflater) -> ActivityLayoutToLoadFragmentBinding =
        ActivityLayoutToLoadFragmentBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        enableToolbar(true, "Session")
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.container, SessionFragment.newInstance(courseId))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.repo_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_link) {
            // TODO: handle link icon clicked
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_COURSE_ID = "extra_course_id"

        fun newIntent(context: Context, courseId: LongId<Course>): Intent =
            Intent(context, SessionActivity::class.java).apply {
                putExtra(EXTRA_COURSE_ID, courseId.get())
            }
    }

}
