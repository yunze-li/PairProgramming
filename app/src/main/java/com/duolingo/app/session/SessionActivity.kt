package com.duolingo.app.session

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.duolingo.app.base.BaseActivity
import com.duolingo.app.databinding.ActivitySessionBinding
import com.duolingo.app.extensions.getLongExtra
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

/** Activity of a learning session */
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
        val sessionRouter = SessionRouter(this)

        viewModel.apply {
            whileStarted(sessionRoute) {
                it.invoke(sessionRouter)
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
