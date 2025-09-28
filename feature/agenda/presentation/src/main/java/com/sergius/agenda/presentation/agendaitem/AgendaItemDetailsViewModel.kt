package com.sergius.agenda.presentation.agendaitem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.agenda.data.AgendaItemDetailsState
import com.sergius.agenda.data.mappers.toEvent
import com.sergius.agenda.data.mappers.toReminder
import com.sergius.agenda.data.mappers.toTask
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.LocalAgendaDataSource
import com.sergius.core.domain.RemoteAgendaDataSource
import com.sergius.core.domain.model.PickerType
import com.sergius.core.domain.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
class AgendaItemDetailsViewModel(
    private val localDataStore: LocalAgendaDataSource,
    private val remoteDataStore: RemoteAgendaDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(AgendaItemDetailsState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AgendaItemDetailsState()
        )

    fun onAction(action: AgendaItemDetailsAction) {
        when (action) {
            is AgendaItemDetailsAction.OnSaveClick -> {
                upsertItem(action.itemType)
            }

            is AgendaItemDetailsAction.OnToggleTimerDialogVisibility -> {
                _state.update {
                    if (action.pickerType == PickerType.PickerFrom || action.pickerType == PickerType.PickerAt) {
                        it.copy(
                            showTimerDialog = !it.showTimerDialog,
                            showToTimerDialog = false
                        )
                    } else {
                        it.copy(
                            showToTimerDialog = !it.showToTimerDialog,
                            showTimerDialog = false
                        )
                    }
                }
            }

            is AgendaItemDetailsAction.OnToggleDateDialogVisibility -> {
                _state.update {
                    if (action.pickerType == PickerType.PickerFrom || action.pickerType == PickerType.PickerAt) {
                        it.copy(
                            showDateDialog = !it.showDateDialog,
                            showDateToDialog = false
                        )
                    } else {
                        it.copy(
                            showDateToDialog = !it.showDateToDialog,
                            showDateDialog = false,
                        )
                    }
                }
            }

            is AgendaItemDetailsAction.OnToggleReminderDropdownVisibility -> {
                _state.update {
                    it.copy(
                        showReminderDropdown = !it.showReminderDropdown
                    )
                }
            }

            is AgendaItemDetailsAction.OnDropdownItemClick -> {
                _state.update {
                    it.copy(
                        reminderSelectedOption = action.item,
                        showReminderDropdown = false
                    )
                }
            }

            else -> Unit
        }
    }

    private fun upsertItem(itemType: AgendaItemType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (itemType) {
                AgendaItemType.TASK -> {
                    val item = _state.value.toTask()
                    localDataStore.upsertTask(item)
                        .onSuccess {
                            remoteDataStore.createTask(item)
                        }
                }

                AgendaItemType.EVENT -> {
                    val item = _state.value.toEvent()
                    localDataStore.upsertEvent(item)
                        .onSuccess {
                            remoteDataStore.upsertEvent(item)
                        }
                }

                AgendaItemType.REMINDER -> {
                    val item = _state.value.toReminder()
                    localDataStore.upsertReminder(item)
                        .onSuccess {
                            remoteDataStore.upsertReminder(item)
                        }
                }
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _state.update {
            it.copy(
                title = newTitle
            )
        }
    }

    fun updateDescription(description: String) {
        _state.update {
            it.copy(
                description = description
            )
        }
    }
}