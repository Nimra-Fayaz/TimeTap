package com.example.timecalculator.model

import java.util.UUID

/**
 * TodoItem represents a Todo with an associated Duration (Timed<String>).
 * This is the app's internal representation combining the API Todo with timing.
 */
data class TodoItem(
    val id: UUID,
    val timedDescription: Timed<String>,
    val isCompleted: Boolean = false
) {
    val description: String
        get() = timedDescription.value

    val duration: Duration
        get() = timedDescription.duration
}

