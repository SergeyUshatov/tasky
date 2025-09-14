package com.sergius.core.database.mapper

import com.sergius.core.database.entity.EventEntity
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.model.AgendaItem
import com.sergius.core.domain.model.Event

fun Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id,
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

fun Event.toAgendaItem(): AgendaItem {
    return AgendaItem(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.from,
        from = this.from,
        to = this.to,
        remindAt = this.remindAt,
        attendeeIds = this.attendeeIds,
        photoKeys = this.photoKeys,
        itemType = AgendaItemType.EVENT
    )
}