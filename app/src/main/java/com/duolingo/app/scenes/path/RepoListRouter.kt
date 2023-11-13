package com.duolingo.app.scenes.path

import androidx.appcompat.app.AppCompatActivity
import com.duolingo.app.scenes.repo.RepoActivity
import javax.inject.Inject

class RepoListRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    fun routeToRepo(repoId: Long, repoName: String, userName: String) {
        activity.startActivity(
            RepoActivity.newIntent(
                activity.applicationContext,
                repoId,
                repoName,
                userName
            )
        )
    }

}