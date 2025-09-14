package com.sergius.core.database.mapper

import com.sergius.core.database.entity.ReminderEntity
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.model.AgendaItem
import com.sergius.core.domain.model.Reminder

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        time = this.time
    )
}

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        updatedAt = this.updatedAt,
        time = this.time
    )
}

fun Reminder.toAgendaItem(): AgendaItem {
    return AgendaItem(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.time,
        remindAt = this.remindAt,
        itemType = AgendaItemType.REMINDER
    )
}