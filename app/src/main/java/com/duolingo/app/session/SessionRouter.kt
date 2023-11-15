package com.duolingo.app.session

import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class SessionRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    fun routeToLink(url: String) {
//        activity.showUrlInBrowser(url)
    }

}