package com.sergius.core.database.mapper

import com.sergius.core.database.entity.EventEntity
import com.sergius.core.domain.model.Event
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id ?: Uuid.random().toString(),
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        from = this.from,
        to = this.to,
        attendeeIds = this.attendeeIds,
        photoKeys = this.photoKeys
    )
}

fun EventEntity.toEvent(): Event {
    return Event(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        updatedAt = this.updatedAt,
        from = this.from,
        to = this.to,
        attendeeIds = this.attendeeIds,
        photoKeys = this.photoKeys
    )
}