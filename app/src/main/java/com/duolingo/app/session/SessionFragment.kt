package com.duolingo.app.session

import android.os.Bundle
import com.duolingo.app.databinding.SessionFragmentBinding
import com.duolingo.app.extensions.build
import com.duolingo.app.extensions.getLongArg
import com.duolingo.app.mvvm.MvvmFragment
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

class SessionFragment : MvvmFragment<SessionFragmentBinding>(SessionFragmentBinding::inflate) {

    @Inject lateinit var viewModelFactory: SessionViewModel.SessionPresenterFactory

    private val viewModel:SessionViewModel by lazy {
        viewModelFactory.create(LongId(getLongArg(ARGS_COURSE_ID) ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(binding: SessionFragmentBinding, savedInstanceState: Bundle?) {
        whileStarted(viewModel.sessionUiModel) {
            binding.uiLanguageName.text = it.uiLanguageText
            binding.learningLanguageName.text = it.learningLanguageText
        }
    }

    companion object {
        private const val ARGS_COURSE_ID = "args_course_id"

        fun newInstance(courseId: Long): SessionFragment =
            SessionFragment().build { putLong(ARGS_COURSE_ID, courseId) }
    }
}
