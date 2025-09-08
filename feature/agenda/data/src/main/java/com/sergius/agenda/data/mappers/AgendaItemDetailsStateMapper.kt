package com.sergius.agenda.data.mappers

import androidx.compose.material3.ExperimentalMaterial3Api
import com.sergius.agenda.data.AgendaItemDetailsState
import com.sergius.agenda.data.DATE_FORMAT
import com.sergius.agenda.data.convertMillisToDate
import com.sergius.agenda.data.toFormattedTime
import com.sergius.core.domain.ReminderItem
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.time.ExperimentalTime

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalTime::class
)
fun AgendaItemDetailsState.toTask(): Task {
    val formatter = SimpleDateFormat("$DATE_FORMAT hh:mm", Locale.getDefault())
    val dateLong = datePickerState.selectedDateMillis ?: 0L
    val date = dateLong.convertMillisToDate()
    val time = timePickerState.toFormattedTime()
    val datetime = "$date $time"
    val startTimeInMillis = formatter.parse(datetime).toInstant()

    val reminder: ReminderItem = ReminderItem.entries
        .first { this.reminderSelectedOption == it.text }
    val remindAt = startTimeInMillis.minus(reminder.longVal, ChronoUnit.MILLIS)

    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = remindAt.toEpochMilli(),
        time = startTimeInMillis.toEpochMilli(),
        isDone = this.isDone
    )
}

fun AgendaItemDetailsState.toEvent(): Event {
    return Event(
        id = this.id
    )
}


fun AgendaItemDetailsState.toReminder(): Reminder {
    return Reminder(
        id = this.id
    )
}