package com.sergius.agenda.data.mappers

import androidx.compose.material3.ExperimentalMaterial3Api
import com.sergius.agenda.data.AgendaItemDetailsState
import com.sergius.agenda.data.DATE_FORMAT
import com.sergius.agenda.data.convertMillisToDate
import com.sergius.agenda.data.toFormattedTime
import com.sergius.core.domain.ReminderItem
import com.sergius.core.domain.model.AgendaItemDetails
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

    val details = if (this.details == null) {
        AgendaItemDetails.TaskDetails(isDone = false)
    } else {
        (this.details as AgendaItemDetails.TaskDetails)
    }

    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = remindAt.toEpochMilli(),
        time = startTimeInMillis.toEpochMilli(),
        isDone = details.isDone,
    )
}
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalTime::class
)

fun AgendaItemDetailsState.toEvent(): Event {
    val formatter = SimpleDateFormat("$DATE_FORMAT hh:mm", Locale.getDefault())
    val startDateLong = datePickerState.selectedDateMillis ?: 0L
    val startDate = startDateLong.convertMillisToDate()
    val startTime = timePickerState.toFormattedTime()
    val startDatetime = "$startDate $startTime"
    val startTimeInMillis = formatter.parse(startDatetime).toInstant()

    val endDateLong = dateToPickerState.selectedDateMillis ?: 0L
    val endDate = endDateLong.convertMillisToDate()
    val endTime = toTimePickerState.toFormattedTime()
    val endDatetime = "$endDate $endTime"
    val endTimeInMillis = formatter.parse(endDatetime).toInstant()

    val reminder: ReminderItem = ReminderItem.entries
        .first { this.reminderSelectedOption == it.text }
    val remindAt = startTimeInMillis.minus(reminder.longVal, ChronoUnit.MILLIS)

    return Event(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt.toEpochMilli(),
        from = startTimeInMillis?.toEpochMilli() ?: 0L,
        to = endTimeInMillis.toEpochMilli() ?: 0L,
        attendeeIds = emptyList(),
        photoKeys = emptyList(),
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalTime::class
)
fun AgendaItemDetailsState.toReminder(): Reminder {
    val formatter = SimpleDateFormat("$DATE_FORMAT hh:mm", Locale.getDefault())
    val dateLong = datePickerState.selectedDateMillis ?: 0L
    val date = dateLong.convertMillisToDate()
    val time = timePickerState.toFormattedTime()
    val datetime = "$date $time"
    val startTimeInMillis = formatter.parse(datetime).toInstant()

    val reminder: ReminderItem = ReminderItem.entries
        .first { this.reminderSelectedOption == it.text }
    val remindAt = startTimeInMillis.minus(reminder.longVal, ChronoUnit.MILLIS)

    return Reminder(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = remindAt.toEpochMilli(),
        time = startTimeInMillis.toEpochMilli(),
    )
}