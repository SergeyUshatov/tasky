package com.sergius.core.domain.model

import java.time.LocalDateTime

typealias Attendee = String
typealias Photo = String
sealed interface AgendaItemDetails {
    data class TaskDetails(val isDone: Boolean = false) : AgendaItemDetails
    data class EventDetails(
        val toTime: LocalDateTime,
        val attendees: List<Attendee>,
        val photos: List<Photo>
    ) : AgendaItemDetails
    data object ReminderDetails: AgendaItemDetails
}