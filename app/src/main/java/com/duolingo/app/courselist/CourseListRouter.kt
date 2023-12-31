package com.duolingo.app.courselist

import androidx.appcompat.app.AppCompatActivity
import com.duolingo.app.sessionlist.SessionListActivity
import com.duolingo.domain.model.Course
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

class CourseListRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    fun routeToCourse(courseId: LongId<Course>) {
        activity.startActivity(
            SessionListActivity.newIntent(
                activity.applicationContext,
                courseId,
            )
        )
    }

}