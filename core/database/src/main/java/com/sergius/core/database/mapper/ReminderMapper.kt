package com.sergius.core.database.mapper

import com.sergius.core.database.entity.ReminderEntity
import com.sergius.core.domain.model.Reminder
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id ?: Uuid.random().toString(),
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