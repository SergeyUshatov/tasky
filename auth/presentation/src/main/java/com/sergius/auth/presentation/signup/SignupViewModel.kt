package com.sergius.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sergius.domain.UserDataValidator

class SignupViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {

    var state by mutableStateOf(SignupState())
        private set

    fun onAction(action: SignUpScreenAction) {

    }
}