package com.duolingo.app.courselist

import com.duolingo.app.base.BaseViewModel
import com.duolingo.domain.repository.CourseRepository
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

    val courseElements = Flowable.defer {
        courseRepository.observeAllAvailableCourses()
            .map { courses ->
                courses.map { course ->
                    CourseElement(
                        uiLanguageText = course.uiLanguage.name,
                        learningLanguageText = course.learningLanguage.name,
                        onCourseClicked = { router.routeToCourse(course.id) }
                    )
                }
            }
    }

    /** Element for course item view */
    data class CourseElement(
        val uiLanguageText: String,
        val learningLanguageText: String,
        val onCourseClicked: () -> Unit,
    )
}
