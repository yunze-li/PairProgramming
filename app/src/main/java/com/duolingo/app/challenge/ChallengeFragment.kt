package com.duolingo.app.challenge

import android.os.Bundle
import android.view.View
import com.duolingo.app.databinding.FragmentChallengeBinding
import com.duolingo.app.extensions.build
import com.duolingo.app.extensions.getSerializableArg
import com.duolingo.app.extensions.setText
import com.duolingo.app.mvvm.MvvmFragment
import com.duolingo.domain.model.Challenge
import javax.inject.Inject

/** Screen to show a single challenge */
class ChallengeFragment : MvvmFragment<FragmentChallengeBinding>(FragmentChallengeBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ChallengeFragmentViewModel.Factory
    private val viewModel:ChallengeFragmentViewModel by lazy {
        viewModelFactory.create(getSerializableArg(ARGS_CHALLENGE) as Challenge)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(binding: FragmentChallengeBinding, savedInstanceState: Bundle?) {
        viewModel.apply {
            whileStarted(uiState) {
                binding.targetWord.setText(it.questionText)
                binding.optionText1.setText(it.option1Text)
                binding.optionCard1.setOnClickListener { _ -> it.onOptionCard1Selected() }
                binding.optionText2.setText(it.option2Text)
                binding.optionCard2.setOnClickListener { _ -> it.onOptionCard2Selected() }
                binding.optionText3.setText(it.option3Text)
                binding.optionCard3.setOnClickListener { _ -> it.onOptionCard3Selected() }
                binding.optionText4.setText(it.option4Text)
                binding.optionCard4.setOnClickListener { _ -> it.onOptionCard4Selected() }
            }
            whileStarted(errorMessageShowState) {
                when (it) {
                    ChallengeFragmentViewModel.ErrorMessageShowState.SHOW ->
                        binding.errorMessageCard.visibility = View.VISIBLE
                    ChallengeFragmentViewModel.ErrorMessageShowState.HIDE ->
                        binding.errorMessageCard.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        const val TAG= "challenge"
        private const val ARGS_CHALLENGE= "args_challenge"

        fun newInstance(challenge: Challenge): ChallengeFragment =
            ChallengeFragment().build { putSerializable(ARGS_CHALLENGE, challenge) }
    }
}
