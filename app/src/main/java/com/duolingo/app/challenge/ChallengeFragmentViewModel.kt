package com.duolingo.app.challenge

import com.duolingo.app.base.BaseViewModel
import com.duolingo.app.string.StringUiModelFactory
import com.duolingo.app.ui.UiModel
import com.duolingo.domain.model.Challenge
import com.duolingo.rxjava.processor.RxProcessor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Flowable

/** View model of [ChallengeFragment] */
class ChallengeFragmentViewModel
@AssistedInject constructor(
    @Assisted private val challenge: Challenge,
    private val challengeBridge: ChallengeBridge,
    rxProcessorFactory: RxProcessor.Factory,
    private val stringUiModelFactory: StringUiModelFactory,
) : BaseViewModel() {


    val uiState = Flowable.defer {
        Flowable.just(
            UiState(
                questionText = stringUiModelFactory.value(challenge.question),
                option1Text = stringUiModelFactory.value(challenge.option1),
                onOptionCard1Selected = { onOptionSelected(challenge.option1, challenge.answer) },
                option2Text = stringUiModelFactory.value(challenge.option2),
                onOptionCard2Selected = { onOptionSelected(challenge.option2, challenge.answer) },
                option3Text = stringUiModelFactory.value(challenge.option3),
                onOptionCard3Selected = { onOptionSelected(challenge.option3, challenge.answer) },
                option4Text = stringUiModelFactory.value(challenge.option4),
                onOptionCard4Selected = { onOptionSelected(challenge.option4, challenge.answer) },
            )
        )
    }

    private val errorMessageShowStateProcessor = rxProcessorFactory.publish<ErrorMessageShowState>()
    val errorMessageShowState = errorMessageShowStateProcessor.observe()

    private fun onOptionSelected(selectedOption: String, answer: String) {
        if (selectedOption == answer) {
            // answer is correct, hide error message if any and go to next challenge
            errorMessageShowStateProcessor.offer(ErrorMessageShowState.HIDE)
            challengeBridge.onChallengeCompleted()
        } else {
            // answer is incorrect, show error message
            errorMessageShowStateProcessor.offer(ErrorMessageShowState.SHOW)
        }
    }

    /** Data class for the UI state of the [ChallengeFragment]. */
    data class UiState(
        val questionText: UiModel<String>,
        val option1Text: UiModel<String>,
        val onOptionCard1Selected: () -> Unit,
        val option2Text: UiModel<String>,
        val onOptionCard2Selected: () -> Unit,
        val option3Text: UiModel<String>,
        val onOptionCard3Selected: () -> Unit,
        val option4Text: UiModel<String>,
        val onOptionCard4Selected: () -> Unit,
    )

    /** Enum class indicate the error message show state */
    enum class ErrorMessageShowState {
        SHOW, HIDE
    }

    @AssistedFactory
    interface Factory {
        fun create(challenge: Challenge): ChallengeFragmentViewModel
    }
}