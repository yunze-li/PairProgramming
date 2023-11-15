package com.duolingo.app.courselist

import androidx.appcompat.app.AppCompatActivity
import com.duolingo.app.session.SessionActivity
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

class CourseListRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    fun routeToSession(courseId: LongId<Course>) {
        activity.startActivity(
            SessionActivity.newIntent(
                activity.applicationContext,
                courseId,
            )
        )
    }

}