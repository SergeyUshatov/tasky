package com.sergius.tasky

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.core.domain.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                _state.value = _state.value.copy(isCheckingAuth = true)
                _state.value = _state.value.copy(
                    isLoggedIn = sessionStorage.get() != null
                )
                _state.value = _state.value.copy(isCheckingAuth = false)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = MainState()
        )
}