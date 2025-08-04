package com.sergius.agenda.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class AgendaViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        AgendaState(
            month = getCurrentMonth(),
            days = getDaysOfMonth()
        )
    )
    val state = _state
        .onStart {
            getDaysOfMonth()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = AgendaState(
                month = getCurrentMonth(),
                days = getDaysOfMonth()
            )
        )

    private fun getCurrentMonth(): Month {
        return now().month
    }

    private fun now(): LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    private fun getDaysOfMonth(): List<CalendarUi> {
        val startDay = now().minus(15, DateTimeUnit.DAY)
        val endDay = startDay.plus(30, DateTimeUnit.DAY)

        return (startDay..endDay).map { date ->
            CalendarUi(
                month = date.month,
                day = date.day,
                dayOfWeek = date.dayOfWeek.name.take(1)
            )
        }
    }

    fun onAction(action: AgendaAction) {
        TODO("Not yet implemented")
    }
}