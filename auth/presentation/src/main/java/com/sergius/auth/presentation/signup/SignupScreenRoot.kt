package com.sergius.auth.presentation.signup

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergius.auth.presentation.EmailFieldState
import com.sergius.auth.presentation.NameFieldState
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
fun SignupScreenRoot(
    onLoginClick: () -> Unit,
    onSignupSuccess: () -> Unit,
    viewModel: SignupViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SignupEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }

            is SignupEvent.Success -> {
                keyboardController?.hide()
                onSignupSuccess()
                Toast.makeText(context, "You are signed up", Toast.LENGTH_LONG).show()
            }
        }
    }

    SignupScreen(
        state = state.value,
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
    onAction: (SignUpScreenAction) -> Unit,
    state: SignupState,
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeLayout(state = state, onAction = onAction)
        }
        else -> {
            PortraitLayout(state = state, onAction = onAction)
        }
    }
}

@Composable
private fun PortraitLayout(
    onAction: (SignUpScreenAction) -> Unit,
    state: SignupState,
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
                SignupForm(state, onAction)
            }
        }
    }
}

@Composable
private fun LandscapeLayout(
    onAction: (SignUpScreenAction) -> Unit,
    state: SignupState,
) {
    Scaffold { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenTitleText()
            }

            Column(
                modifier = Modifier
                    .padding( top = 16.dp, end = 16.dp)
                    .weight(2f)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                SignupForm(state, onAction)
            }
        }
    }
}

@Composable
private fun SignupForm(
    state: SignupState,
    onAction: (SignUpScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NameField(state.nameState, onAction)
        EmailField(state.emailState, onAction)
        PasswordField(state.passwordState, onAction)

        Spacer(modifier = Modifier.height(4.dp))
        SignUpButton(state.canSignup, state.isSigningUp, onAction)

        Spacer(modifier = Modifier.height(4.dp))
        LoginAsExistingUser(onAction)
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
    canSignup: Boolean,
    isSigningUp: Boolean,
    onAction: (SignUpScreenAction) -> Unit
) {
    TaskyActionButton(
        text = stringResource(R.string.get_started),
        isLoading = isSigningUp,
        enabled = canSignup && !isSigningUp,
        onClick = {
            onAction(SignUpScreenAction.OnSignUpClick)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    )
}

@Composable
private fun PasswordField(
    state: PasswordFieldState,
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
    state: EmailFieldState,
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
    state: NameFieldState,
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
@Preview(
    name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    )
@Composable
private fun SignupScreenPreview() {
    TaskyTheme {
        SignupScreen(
            state = SignupState(),
            onAction = {}
        )
    }
}