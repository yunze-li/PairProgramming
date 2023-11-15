package com.duolingo.app.sessionlist

import android.os.Bundle
import com.duolingo.app.databinding.FragmentSessionListBinding
import com.duolingo.app.extensions.build
import com.duolingo.app.extensions.getLongArg
import com.duolingo.app.mvvm.MvvmFragment
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

class SessionListFragment : MvvmFragment<FragmentSessionListBinding>(FragmentSessionListBinding::inflate) {

    @Inject lateinit var viewModelFactory: SessionListViewModel.Factory

    private val viewModel: SessionListViewModel by lazy {
        viewModelFactory.create(LongId(getLongArg(ARGS_COURSE_ID) ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(binding: FragmentSessionListBinding, savedInstanceState: Bundle?) {
        val sessionElementAdapter = SessionElementAdapter()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = sessionElementAdapter

        whileStarted(viewModel.sessionElements) { sessionElementAdapter.setSessionElement(it) }
    }

    companion object {
        private const val ARGS_COURSE_ID = "args_course_id"

        /** Get new instance of [SessionListFragment] */
        fun newInstance(courseId: Long): SessionListFragment =
            SessionListFragment().build { putLong(ARGS_COURSE_ID, courseId) }
    }
}
