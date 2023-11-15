package com.duolingo.app.courselist

import com.duolingo.app.base.BaseViewModel
import com.duolingo.app.ui.UiModel
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Language
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.rxjava.flowable.captureLatest
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class CourseListViewModel
@Inject constructor(
    private val courseRepository: CourseRepository,
    private val router: CourseListRouter,
) : BaseViewModel() {

    fun configure() = configureOnce {
        // TODO: add something in here
    }

    val courseUiModel = Flowable.defer {
        courseRepository.observeAllAvailableCourses()
            .map { courses ->
                courses.map { course ->
                    CourseUiModel(
                        uiLanguageText = course.uiLanguage.name,
                        learningLanguageText = course.learningLanguage.name,
                        onCourseClick = { router.routeToSession(course.id) }
                    )
                }
            }
    }

    /** Ui model for a single course. */
    data class CourseUiModel(
        val uiLanguageText: String,
        val learningLanguageText: String,
        val onCourseClick: () -> Unit,
    )
}
