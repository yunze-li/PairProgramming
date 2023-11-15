package com.duolingo.app.courselist

import com.duolingo.app.base.BaseViewModel
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.Language
import com.duolingo.domain.model.id.LongId
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

    val courseFlowable = Flowable.just(
        listOf(
            Course(LongId(1L), Language.JAPANESE, Language.CHINESE, emptyList()),
            Course(LongId(2L), Language.CHINESE, Language.ENGLISH, emptyList()),
            Course(LongId(3L), Language.ENGLISH, Language.JAPANESE, emptyList()),
        )
    )

}
