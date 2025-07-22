package com.sergius.auth.presentation.signup

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergius.auth.presentation.R
import com.sergius.core.presentation.designsystem.CheckIcon
import com.sergius.core.presentation.designsystem.elements.TaskyActionButton
import com.sergius.core.presentation.designsystem.elements.TaskyPasswordField
import com.sergius.core.presentation.designsystem.elements.TaskyTextField
import com.sergius.core.presentation.designsystem.theme.TaskyCheckIconColor
import com.sergius.core.presentation.designsystem.theme.TaskyLightLink
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreenRoot(
    onLoginClick: () -> Unit,
    viewModel: SignupViewModel = koinViewModel(),
) {
    SignupScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is SignUpScreenAction.OnLoginClick -> onLoginClick()
                else -> Unit
            }

            viewModel.onAction(action)
        },
    )
}

@Composable
private fun SignupScreen(
    state: SignupState,
    onAction: (SignUpScreenAction) -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary),
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
                ) {
                    NameField(state, onAction)

                    Spacer(modifier = Modifier.height(16.dp))
                    EmailField(state, onAction)

                    Spacer(modifier = Modifier.height(16.dp))
                    PasswordField(state, onAction)

                    Spacer(modifier = Modifier.height(32.dp))
                    SignUpButton(state, onAction)

                    Spacer(modifier = Modifier.height(16.dp))
                    LoginAsExistingUser(onAction)
                }
            }
        }
    }
}

@Composable
private fun ScreenTitleText() {
    Text(
        text = stringResource(R.string.create_your_account),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    )
}

@Composable
private fun LoginAsExistingUser(onAction: (SignUpScreenAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.already_have_an_account),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Text(
            text = stringResource(R.string.log_in),
            style = MaterialTheme.typography.labelSmall,
            color = TaskyLightLink,
            modifier = Modifier
                .clickable {
                    onAction(SignUpScreenAction.OnLoginClick)
                }
        )
    }
}

@Composable
private fun SignUpButton(
    state: SignupState,
    onAction: (SignUpScreenAction) -> Unit
) {
    TaskyActionButton(
        text = "GET STARTED",
        isLoading = state.isSigningUp,
        enabled = state.canSignup && !state.isSigningUp,
        onClick = {
            onAction(SignUpScreenAction.OnGetStartedClick)
        },
        modifier = Modifier
            .fillMaxWidth(),
    )
}

@Composable
private fun PasswordField(
    state: SignupState,
    onAction: (SignUpScreenAction) -> Unit
) {
    TaskyPasswordField(
        state = state.password,
        isFocused = state.isPasswordFocused,
        onFocusChanged = { isFocused ->
            onAction(SignUpScreenAction.OnPasswordFocusChanged(isFocused))
        },
        isPasswordVisible = state.isPasswordVisible,
        placeholder = stringResource(R.string.password),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onTogglePasswordVisibility = {
            onAction(SignUpScreenAction.OnTogglePasswordVisibility)
        }
    )
}

@Composable
private fun EmailField(
    state: SignupState,
    onAction: (SignUpScreenAction) -> Unit
) {
    TaskyTextField(
        state = state.email,
        endIcon = if (state.isEmailValid) CheckIcon else null,
        endIconTint = TaskyCheckIconColor,
        isFocused = state.isEmailFocused,
        onFocusChanged = { isFocused ->
            onAction(SignUpScreenAction.OnEmailFocusChanged(isFocused))
        },
        keyboardType = KeyboardType.Email,
        placeholder = stringResource(R.string.email_address),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun NameField(
    state: SignupState,
    onAction: (SignUpScreenAction) -> Unit
) {
    TaskyTextField(
        state = state.name,
        endIcon = if (state.name.text.isNotEmpty()) CheckIcon else null,
        endIconTint = TaskyCheckIconColor,
        isFocused = state.isNameFocused,
        onFocusChanged = { isFocused ->
            onAction(SignUpScreenAction.OnNameFocusChanged(isFocused))
        },
        placeholder = stringResource(R.string.name),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}


@Preview
@Composable
private fun SignupScreenPreview() {
    SignupScreen(
        state = SignupState(),
        onAction = {}
    )
}