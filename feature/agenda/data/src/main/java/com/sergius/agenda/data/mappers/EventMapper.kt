package com.sergius.agenda.data.mappers

import com.sergius.agenda.data.dto.CreateEventRequest
import com.sergius.agenda.data.dto.EventDto
import com.sergius.core.domain.model.Event
import java.time.Instant

fun Event.toCreateEventRequest() = CreateEventRequest(
    id = id,
    title = title,
    description = description,
    from = Instant.ofEpochMilli(from).toString(),
    to = Instant.ofEpochMilli(to).toString(),
    remindAt = Instant.ofEpochMilli(remindAt).toString(),
    updatedAt = Instant.ofEpochMilli(updatedAt ?: 0).toString(),
    attendeeIds = attendeeIds,
    photoKeys = photoKeys
)

fun EventDto.toEvent() = Event(
    id = id,
    title = title,
    description = description,
    from = from.toLong(),
    to = to.toLong(),
    attendeeIds = attendees.map { it.userId },
    photoKeys = photoKeys.map { it.key },
    remindAt = attendees.first().remindAt.toLong()
)