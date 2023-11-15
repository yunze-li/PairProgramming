package com.duolingo.app.challenge

import android.os.Bundle
import com.duolingo.app.databinding.FragmentChallengeBinding
import com.duolingo.app.extensions.build
import com.duolingo.app.extensions.getSerializableArg
import com.duolingo.app.mvvm.MvvmFragment
import com.duolingo.domain.model.Challenge

class ChallengeFragment : MvvmFragment<FragmentChallengeBinding>(FragmentChallengeBinding::inflate) {

//    @Inject lateinit var viewModelFactory: SessionViewModel.Factory
//
//    private val viewModel:SessionViewModel by lazy {
//        viewModelFactory.create(LongId(getLongArg(ARGS_SESSION_ID) ))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(binding: FragmentChallengeBinding, savedInstanceState: Bundle?) {
        when (val challenge = getSerializableArg(ARGS_CHALLENGE) as Challenge) {
            is Challenge.ForwardTranslationChallenge -> {
                binding.optionText1.text = challenge.data.first
                binding.optionText2.text = challenge.data.second
                binding.optionText3.text = challenge.data.third
                binding.optionText4.text = challenge.data.fourth
            }
            else -> {
                // TODO: add support in here
            }
        }
    }

    companion object {
        private const val ARGS_CHALLENGE= "args_challenge"

        fun newInstance(challenge: Challenge): ChallengeFragment =
            ChallengeFragment().build { putSerializable(ARGS_CHALLENGE, challenge) }
    }
}
