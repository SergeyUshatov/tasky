package com.sergius.agenda.presentation.agendaoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.agenda.presentation.mapper.toAgendaItemUi
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.LocalAgendaDataSource
import com.sergius.core.domain.RemoteEventDataSource
import com.sergius.core.domain.RemoteReminderDataSource
import com.sergius.core.domain.RemoteTaskDataSource
import com.sergius.core.domain.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class AgendaViewModel(
    private val localDataStore: LocalAgendaDataSource,
    private val taskRemoteDataStore: RemoteTaskDataSource,
    private val eventRemoteDataStore: RemoteEventDataSource,
    private val reminderRemoteDataStore: RemoteReminderDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(AgendaState())
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val state = combine(
        _state,
        _selectedDate.flatMapLatest { date ->
            // whenever the date changes, cancel the old flow and start collecting the new one
            localDataStore.getAgendaForDate(date)
        }
    ) { uiState, agendaItems ->
        uiState.copy(
            items = agendaItems.map { it.toAgendaItemUi() },
            month = getCurrentMonth(),
            days = getDaysOfMonth(),
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AgendaState(isLoading = true)
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
        return _selectedDate.value.month.name
    }

    private fun getDaysOfMonth(): List<CalendarUi> {
        return (1.._selectedDate.value.month.maxLength())
            .map {
                val date = LocalDate.of(_selectedDate.value.year, _selectedDate.value.month, it)
                CalendarUi(
                    year = date.year,
                    month = date.month,
                    day = date.dayOfMonth,
                    dayOfWeek = date.dayOfWeek.name.take(1),
                    isSelected = it == _selectedDate.value.dayOfMonth
                )
            }
    }

    fun onAction(action: AgendaAction) {
        when (action) {
            is AgendaAction.OnCreateAgendaItemClick -> {
                _state.update { it.copy(fabExpanded = !_state.value.fabExpanded) }
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
                        AgendaItemType.TASK -> {
                            localDataStore.deleteTask(itemId)
                                .onSuccess {
                                    taskRemoteDataStore.deleteTask(itemId)
                                }
                        }
                        AgendaItemType.EVENT -> {
                            localDataStore.deleteEvent(itemId)
                                .onSuccess {
                                    eventRemoteDataStore.deleteEvent(itemId)
                                }
                        }
                        AgendaItemType.REMINDER -> {
                            localDataStore.deleteReminder(itemId)
                                .onSuccess {
                                    reminderRemoteDataStore.deleteReminder(itemId)
                                }
                        }
                    }

                    _state.update {
                        it.copy(expandedItemId = null)
                    }
                    refreshAgendaItems()
                }
            }

            is AgendaAction.OnDayClick -> {
                _selectedDate.update {
                    LocalDate.of(action.date.year, action.date.month, action.date.day)
                }
            }

            else -> Unit
        }
    }
}