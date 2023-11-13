package com.duolingo.domain.usecases.base

import io.reactivex.rxjava3.core.Scheduler

/**
 * This class represents a scheduler for [UseCase].
 */
data class UseCaseScheduler(val run: Scheduler, val post: Scheduler)
