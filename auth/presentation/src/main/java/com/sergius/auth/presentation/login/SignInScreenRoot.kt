package com.sergius.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergius.auth.presentation.EmailFieldState
import com.sergius.auth.presentation.PasswordFieldState
import com.sergius.auth.presentation.R
import com.sergius.core.presentation.designsystem.CheckIcon
import com.sergius.core.presentation.designsystem.elements.TaskyActionButton
import com.sergius.core.presentation.designsystem.elements.TaskyPasswordField
import com.sergius.core.presentation.designsystem.elements.TaskyTextField
import com.sergius.core.presentation.designsystem.theme.TaskyCheckIconColor
import com.sergius.core.presentation.designsystem.theme.TaskyLightLink
import com.sergius.core.presentation.designsystem.theme.TaskyTheme
import com.sergius.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreenRoot(
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    viewModel.isComposing.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }

            is LoginEvent.Success -> {
                keyboardController?.hide()
                Toast.makeText(context, "You are logged in", Toast.LENGTH_LONG).show()
            }
        }
    }

    SignInScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is SignInScreenAction.OnSignUpClick -> onSignUpClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SignInScreen(
    state: LoginState,
    onAction: (SignInScreenAction) -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            ScreenTitleText()

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EmailField(state.emailState, onAction)
                    PasswordField(state.passwordState, onAction)

                    Spacer(modifier = Modifier.height(4.dp))
                    LoginButton(state.canLogin, state.isLoggingIn, onAction)

                    Spacer(modifier = Modifier.height(4.dp))
                    SignUpAsNewUser(onAction)
                }
            }
        }
    }
}

@Composable
private fun SignUpAsNewUser(onAction: (SignInScreenAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.don_t_have_an_account),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.labelSmall,
            color = TaskyLightLink,
            modifier = Modifier
                .clickable {
                    onAction(SignInScreenAction.OnSignUpClick)
                }
        )
    }
}

@Composable
private fun LoginButton(
    canLogin: Boolean,
    isLoggingIn: Boolean,
    onAction: (SignInScreenAction) -> Unit
) {
    TaskyActionButton(
        text = stringResource(R.string.log_in),
        isLoading = isLoggingIn,
        enabled = canLogin && !isLoggingIn,
        onClick = {
            onAction(SignInScreenAction.OnLoginClick)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    )
}

@Composable
private fun PasswordField(
    state: PasswordFieldState,
    onAction: (SignInScreenAction) -> Unit
) {
    TaskyPasswordField(
        state = state.password,
        isFocused = state.isPasswordFocused,
        onFocusChanged = { isFocused ->
            onAction(SignInScreenAction.OnPasswordFocusChanged(isFocused))
        },
        isPasswordVisible = state.isPasswordVisible,
        placeholder = stringResource(R.string.password),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onTogglePasswordVisibility = {
            onAction(SignInScreenAction.OnTogglePasswordVisibility)
        }
    )
}

@Composable
private fun EmailField(
    state: EmailFieldState,
    onAction: (SignInScreenAction) -> Unit
) {
    TaskyTextField(
        state = state.email,
        endIcon = if (state.isEmailValid) CheckIcon else null,
        endIconTint = TaskyCheckIconColor,
        isFocused = state.isEmailFocused,
        onFocusChanged = { isFocused ->
            onAction(SignInScreenAction.OnEmailFocusChanged(isFocused))
        },
        keyboardType = KeyboardType.Email,
        placeholder = stringResource(R.string.email_address),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun ScreenTitleText() {
    Text(
        text = stringResource(R.string.welcome_back),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    )
}

@Preview
@Composable
private fun SignInScreenPreview() {
    TaskyTheme {
        SignInScreen(
            state = LoginState(canLogin = true),
            onAction = {}
        )
    }
}