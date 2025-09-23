package com.sergius.tasky

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.core.domain.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                _state.update { it.copy(isCheckingAuth = true) }
                _state.update {
                    val get = sessionStorage.get()
                    it.copy(isLoggedIn = get != null
                            && get.accessToken.isNotEmpty()
                            && get.accessToken != "UNDEFINED"
                            && get.refreshToken.isNotEmpty()
                            && get.refreshToken != "UNDEFINED"
                            && get.userId.isNotEmpty()
                            && get.userId != "UNDEFINED"
                    )
                }

                _state.update { it.copy(isCheckingAuth = false) }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainState()
        )
}