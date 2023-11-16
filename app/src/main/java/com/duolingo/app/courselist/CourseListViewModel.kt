package com.duolingo.app.courselist

import com.duolingo.app.base.BaseViewModel
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class CourseListViewModel
@Inject constructor(
    private val courseRepository: CourseRepository,
    private val router: CourseListRouter,
) : BaseViewModel() {

    fun configure() = configureOnce {
//        courseRepository.fetchCourse(LongId(1L)).subscribe(
//            { course -> println("YunzeDebug: course: $course") },
//            { error -> println("YunzeDebug: error: $error") }
//        ).unsubscribeOnCleared()
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
