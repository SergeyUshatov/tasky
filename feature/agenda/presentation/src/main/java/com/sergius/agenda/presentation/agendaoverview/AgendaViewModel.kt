package com.sergius.agenda.presentation.agendaoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.agenda.presentation.mapper.toAgendaItemUi
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.LocalAgendaDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
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
                refreshAgendaItems()
            }
            isInitialized = true
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AgendaState()
        )

    private suspend fun refreshAgendaItems() {
        val items = localDataStore.getAgendaForDate(LocalDate.now())
        _state.update {
            it.copy(
                items = items.first().map { it.toAgendaItemUi() }
            )
        }
    }

    private fun getCurrentMonth(): String {
        return LocalDate.now().month.name
    }

    private fun getDaysOfMonth(): List<CalendarUi> {
        val startDay = LocalDate.now().minusDays(15)
        return (0L..30L).map {
            val day = startDay.plusDays(it)
            CalendarUi(
                month = day.month,
                day = day.dayOfMonth,
                dayOfWeek = day.dayOfWeek.name.take(1)
            )
        }
    }

    fun onAction(action: AgendaAction) {
        when (action) {
            is AgendaAction.OnCreateAgendaItemClick -> {
                _state.update { it.copy(fabExpanded = !_state.value.fabExpanded) }
                isInitialized = false
            }

            is AgendaAction.OnToggleMoreActions -> {
                _state.update {
                    it.copy(expandedItemId = action.itemId)
                }
            }

            is AgendaAction.OnDeleteItem -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val itemId = action.item.id
                    when (action.item.itemType) {
                        AgendaItemType.TASK -> localDataStore.deleteTask(itemId)
                        AgendaItemType.EVENT -> localDataStore.deleteEvent(itemId)
                        AgendaItemType.REMINDER -> localDataStore.deleteReminder(itemId)
                    }

                    _state.update {
                        it.copy(expandedItemId = null)
                    }
                    refreshAgendaItems()
                }
            }

            else -> Unit
        }
    }
}