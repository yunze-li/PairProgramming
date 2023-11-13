package com.duolingo.rxjava.queue.priority

/**
 * Priority for a task, from lowest [LOW] to highest [HIGH]. Usage:
 * - [LOW]: tasks that are non urgent and can complete eventually, for example prefetching
 * - [MEDIUM]: tasks that that are required to load an upcoming screen/flow, for example fetching a
 * sentence audio when starting a session
 * - [HIGH]: tasks that have been triggered by user interaction and need to be executed as soon as
 * possible, for example a login request
 */
public enum class Priority {
  LOW,
  MEDIUM,
  HIGH,
}
