package com.sergius.core.domain.model

import java.time.LocalDateTime

sealed interface AgendaItemDetails {
    data class TaskDetails(val isDone: Boolean = false) : AgendaItemDetails
    data class EventDetails(
        val toTime: LocalDateTime,
        val attendees: List<String>,
        val photos: List<String>
    ) : AgendaItemDetails
    data object ReminderDetails: AgendaItemDetails
}