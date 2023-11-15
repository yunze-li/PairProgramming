package com.duolingo.app.session

import com.duolingo.app.base.LoadDataView
import io.reactivex.rxjava3.core.Observable

interface SessionView : LoadDataView<SessionData> {

//    fun intentLoadData(): Observable<String>

    fun intentRefreshData(): Observable<String>

    fun intentErrorRetry(): Observable<String>

    fun intentActionLink(): Observable<Unit>

}