package com.duolingo.app.session

import com.duolingo.app.base.BaseViewModel
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Flowable

class SessionViewModel
@AssistedInject constructor(
    @Assisted private val courseId: LongId<Course>,
    private val router: SessionRouter,
    private val courseRepository: CourseRepository,
) : BaseViewModel() {

    fun configure() = configureOnce {
        // TODO: add something in here
    }

    val sessionUiModel = Flowable.defer {
        courseRepository.observeCourse(courseId)
            .map { SessionUiModel(it.uiLanguage.name, it.learningLanguage.name) }
    }

    /** Ui model for the session screen. */
    data class SessionUiModel(
        val uiLanguageText: String,
        val learningLanguageText: String,
    )

    @AssistedFactory
    interface SessionPresenterFactory {
        fun create(courseId: LongId<Course>): SessionViewModel
    }

}
