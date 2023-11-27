package com.duolingo.app.sessionlist

import androidx.appcompat.app.AppCompatActivity
import com.duolingo.app.session.SessionActivity
import com.duolingo.domain.model.Session
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

/** A router to route different sessions */
class SessionListRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    /** Route to a specific session */
    fun routeToSession(sessionId: LongId<Session>) {
        activity.startActivity(
            SessionActivity.newIntent(
                activity.applicationContext,
                sessionId,
            )
        )
    }

}