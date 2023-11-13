package com.duolingo.domain.usecases

import com.duolingo.domain.exception.PersistenceException
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId
import com.duolingo.domain.repository.CourseRepository
import com.duolingo.domain.usecases.base.FlowableUseCase
import com.duolingo.domain.usecases.base.Logger
import com.duolingo.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject


/**
 * This class is an implementation of [FlowableUseCase] that represents a use case for
 * retrieving the [Course] current learning.
 */
class ObserveCurrentCourse
@Inject internal constructor(
    private val courseRepository: CourseRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : FlowableUseCase<Course, Pair<LongId<User>, LongId<Course>>>(useCaseScheduler, logger) {

    override fun build(param: Pair<LongId<User>, LongId<Course>>): Flowable<Course> {
        val (userId, courseId) = param
        return courseRepository.observeCurrentCourse(userId, courseId)
            .switchIfEmpty(Flowable.error(PersistenceException))
    }

}