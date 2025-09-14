package com.sergius.agenda.data

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.sergius.core.domain.ReminderItem
import com.sergius.core.domain.model.AgendaItemDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
data class AgendaItemDetailsState(
    val id: String = Uuid.random().toString(),
    val title: String = "",
    val description: String = "",
    val showTimerDialog: Boolean = false,
    val timePickerState: TimePickerState = TimePickerState(
        initialHour = 0,
        initialMinute = 0,
        is24Hour = true
    ),
    val showToTimerDialog: Boolean = false,
    val toTimePickerState: TimePickerState = TimePickerState(
        initialHour = 0,
        initialMinute = 0,
        is24Hour = true
    ),
    val showDateDialog: Boolean = false,
    val selectedDate: Long? = null,
    val datePickerState: DatePickerState = DatePickerState(locale = Locale.getDefault()),
    val showDateToDialog: Boolean = false,
    val dateToPickerState: DatePickerState = DatePickerState(locale = Locale.getDefault()),
    val showReminderDropdown: Boolean = false,
    val reminderOptions: List<String> = ReminderItem.entries.map { it.text },
    val reminderSelectedOption: String = reminderOptions.first(),
    val details: AgendaItemDetails? = null
)

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toFormattedTime(): String {
    val hour = if (hour < 10) "0$hour" else hour.toString()
    val minute = if (minute < 10) "0$minute" else minute.toString()
    return "$hour:$minute"
}
const val DATE_FORMAT = "MMM dd, yyyy"

fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return formatter.format(Date(this))
}