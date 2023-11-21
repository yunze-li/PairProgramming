package com.duolingo.app.challenge

import android.os.Bundle
import com.duolingo.app.R
import com.duolingo.app.databinding.FragmentChallengeBinding
import com.duolingo.app.extensions.build
import com.duolingo.app.extensions.getSerializableArg
import com.duolingo.app.mvvm.MvvmFragment
import com.duolingo.domain.model.Challenge
import com.duolingo.domain.model.ChallengeType

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
        val challenge = getSerializableArg(ARGS_CHALLENGE) as Challenge
        binding.targetWord.text = challenge.question
        if (challenge.type == ChallengeType.FORWARD_TRANSLATION) {
            binding.optionText1.text = challenge.option1
            binding.optionText2.text = challenge.option2
            binding.optionText3.text = challenge.option3
            binding.optionText4.text = challenge.option4
        }
    }

    companion object {
        private const val ARGS_CHALLENGE= "args_challenge"

        fun newInstance(challenge: Challenge): ChallengeFragment =
            ChallengeFragment().build { putSerializable(ARGS_CHALLENGE, challenge) }
    }
}
