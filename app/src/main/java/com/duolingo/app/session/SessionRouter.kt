package com.duolingo.app.session

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.duolingo.app.R
import com.duolingo.app.challenge.ChallengeFragment
import com.duolingo.domain.model.Challenge
import javax.inject.Inject

class SessionRouter @Inject internal constructor(private val activity: AppCompatActivity) {

    /** Show next challenge page */
    fun showNextChallenge(challenge: Challenge) {
        showNextChallengePage(
            ChallengeFragment.newInstance(challenge),
            ChallengeFragment.TAG,
        )
    }

    private fun showNextChallengePage(
        fragment: Fragment,
        tag: String,
    ) {
        activity.supportFragmentManager
            .beginTransaction()
            .also {
                it.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                )
            }
            .replace(
                R.id.challenge_container,
                fragment,
                tag,
            )
            .commit()
    }

}