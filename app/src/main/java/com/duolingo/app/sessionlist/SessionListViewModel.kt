package com.duolingo.app.sessionlist

import com.duolingo.app.base.BaseViewModel
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.SessionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Flowable

/** View model of [SessionListFragment] */
class SessionListViewModel
@AssistedInject constructor(
    @Assisted private val courseId: LongId<Course>,
    private val sessionRepository: SessionRepository,
    private val router: SessionListRouter,
) : BaseViewModel() {

    val sessionElements = Flowable.defer {
        sessionRepository.fetchAllSessions(courseId)
            .map { sessions ->
                sessions.map { session ->
                    SessionElement(
                        name = session.displayName,
                        difficultyValueText = session.difficulty.toString(),
                        isCompleted = session.isCompleted,
                        onSessionClicked = { router.routeToSession(session.id) }
                    )
                }
            }
    }

    /** Element for session item view */
    data class SessionElement(
        val name: String,
        val difficultyValueText: String,
        val isCompleted: Boolean,
        val onSessionClicked: () -> Unit,
    )

    @AssistedFactory
    interface Factory {
        fun create(courseId: LongId<Course>): SessionListViewModel
    }
}
