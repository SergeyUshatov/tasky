package com.sergius.agenda.data.mappers

import com.sergius.agenda.data.CreateReminderRequest
import com.sergius.agenda.data.ReminderResponse
import com.sergius.core.domain.model.Reminder
import java.time.Instant

fun Reminder.toCreateReminderRequest() = CreateReminderRequest(
    id = id,
    title = title,
    description = description,
    time = Instant.ofEpochMilli(time).toString(),
    remindAt = Instant.ofEpochMilli(remindAt).toString(),
    updatedAt = Instant.ofEpochMilli(updatedAt ?: 0).toString(),
)

fun ReminderResponse.toReminder() = Reminder(
    id = id,
    title = title,
    description = description,
    time = time.toLong(),
    remindAt = remindAt.toLong(),
)