package com.duolingo.app.scenes.repo

import androidx.appcompat.app.AppCompatActivity
import com.duolingo.app.extensions.showUrlInBrowser
import javax.inject.Inject

class RepoRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    fun routeToLink(url: String) {
        activity.showUrlInBrowser(url)
    }

}