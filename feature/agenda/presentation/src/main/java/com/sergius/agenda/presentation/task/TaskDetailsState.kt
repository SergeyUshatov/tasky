package com.sergius.agenda.presentation.task

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.sergius.core.domain.ReminderItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
data class TaskDetailsState(
    val taskId: String? = null,
    val taskTitle: String = "",
    val isTitleFocused: Boolean = false,
    val taskDescription: String = "",
    val showTimerDialog: Boolean = false,
    val timePickerState: TimePickerState = TimePickerState(initialHour = 0, initialMinute = 0, is24Hour = true),
    val showDateDialog: Boolean = false,
    val selectedDate: Long? = null,
    val datePickerState: DatePickerState = DatePickerState(locale = Locale.getDefault()),
    val showReminderDropdown: Boolean = false,
    val reminderOptions: List<String> = ReminderItem.entries.map { it.text },
    val reminderSelectedOption: String = reminderOptions.first()
)

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toFormattedTime(): String {
    val hour = if (hour < 10) "0$hour" else hour.toString()
    val minute = if (minute < 10) "0$minute" else minute.toString()
    return "$hour:$minute"
}

fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}