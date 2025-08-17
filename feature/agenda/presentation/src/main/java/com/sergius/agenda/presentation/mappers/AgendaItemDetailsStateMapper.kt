package com.sergius.agenda.presentation.mappers

import androidx.compose.material3.ExperimentalMaterial3Api
import com.sergius.agenda.presentation.agendaitem.AgendaItemDetailsState
import com.sergius.agenda.presentation.agendaitem.DATE_FORMAT
import com.sergius.agenda.presentation.agendaitem.convertMillisToDate
import com.sergius.agenda.presentation.agendaitem.toFormattedTime
import com.sergius.core.domain.ReminderItem
import com.sergius.core.domain.dto.EventDto
import com.sergius.core.domain.dto.ReminderDto
import com.sergius.core.domain.dto.TaskDto
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.time.ExperimentalTime

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalTime::class
)
fun AgendaItemDetailsState.toTaskDto(): TaskDto {
    val formatter = SimpleDateFormat("$DATE_FORMAT hh:mm", Locale.getDefault())
    val dateLong = datePickerState.selectedDateMillis ?: 0L
    val date = dateLong.convertMillisToDate()
    val time = timePickerState.toFormattedTime()
    val datetime = "$date $time"
    val startTimeInMillis = formatter.parse(datetime).toInstant()

    val reminder: ReminderItem = ReminderItem.entries
        .first { this.reminderSelectedOption == it.text }
    val remindAt = startTimeInMillis.minus(reminder.longVal, ChronoUnit.MILLIS)

    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = remindAt.toEpochMilli(),
        time = startTimeInMillis.toEpochMilli(),
        isDone = this.isDone
    )
}

fun AgendaItemDetailsState.toEventDto(): EventDto {
    return EventDto(
        id = this.id
    )
}


fun AgendaItemDetailsState.toReminderDto(): ReminderDto {
    return ReminderDto(
        id = this.id
    )
}