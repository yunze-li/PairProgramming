package com.duolingo.app.session

import com.duolingo.rxjava.processor.RxProcessor
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class SessionRouteBridge @Inject constructor(
    private val rxProcessorFactory: RxProcessor.Factory,
) {
    private val routeProcessor = rxProcessorFactory.behavior<SessionRouter.() -> Unit>()
    val route: Flowable<SessionRouter.() -> Unit> = routeProcessor.observe().serialize()

    /** Navigate to specified route for activity */
    fun navigate(route: SessionRouter.() -> Unit) {
        routeProcessor.offer(route)
    }
}