package com.sergius.agenda.presentation.agendaoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.agenda.presentation.agendaitem.AgendaItemUiData
import com.sergius.agenda.presentation.mapper.toAgendaItemUi
import com.sergius.core.domain.LocalAgendaDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class AgendaViewModel(
    private val localDataStore: LocalAgendaDataSource
) : ViewModel() {
    private var isInitialized = false
    private val _state = MutableStateFlow(AgendaState())
    val state = _state
        .onStart {
            if (isInitialized) return@onStart
            _state.update {
                it.copy(
                    month = getCurrentMonth(),
                    days = getDaysOfMonth()
                )
            }
            viewModelScope.launch(Dispatchers.IO) {
                val tasks = localDataStore.getTasks().map { it.toAgendaItemUi() }
                _state.update {
                    it.copy(
                        items = tasks
                    )
                }
            }
            isInitialized = true
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AgendaState()
        )

    private fun getCurrentMonth(): String {
        return now().month.name
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
        when (action) {
            is AgendaAction.OnCreateAgendaItemClick -> {
                _state.update { it.copy(fabExpanded = !_state.value.fabExpanded) }
            }

            is AgendaAction.OnToggleMoreActionsDropdownVisibility -> {
                _state.update {
                    it.copy(showMoreActions = !_state.value.showMoreActions)
                }
            }

            else -> Unit
        }
    }
}