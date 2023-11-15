package com.duolingo.app.session

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.duolingo.app.R
import com.duolingo.app.base.BaseActivity
import com.duolingo.app.challenge.ChallengeFragment
import com.duolingo.app.databinding.ActivitySessionBinding
import com.duolingo.app.extensions.addFragment
import com.duolingo.app.extensions.getLongExtra
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

class SessionActivity : BaseActivity<ActivitySessionBinding>() {

    private val sessionId: Long by lazy { getLongExtra(EXTRA_SESSION_ID) }

    @Inject
    lateinit var viewModelFactory: SessionViewModel.Factory
    private val viewModel:SessionViewModel by lazy {
        viewModelFactory.create(LongId(sessionId))
    }

    override val bindingInflater: (LayoutInflater) -> ActivitySessionBinding =
        ActivitySessionBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        viewModel.apply {
            whileStarted(challenge) {
                addFragment(R.id.container, ChallengeFragment.newInstance(it))
            }
        }.configure()
    }

    companion object {
        private const val EXTRA_SESSION_ID = "extra_session_id"

        fun newIntent(context: Context, sessionId: LongId<Session>): Intent =
            Intent(context, SessionActivity::class.java).apply {
                putExtra(EXTRA_SESSION_ID, sessionId.get())
            }
    }
}
