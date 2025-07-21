package com.sergius.auth.presentation

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
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import com.sergius.core.presentation.designsystem.PasswordHidden
import com.sergius.core.presentation.designsystem.elements.TaskyActionButton
import com.sergius.core.presentation.designsystem.elements.TaskyTextField
import com.sergius.core.presentation.designsystem.theme.TaskyLightLink
import com.sergius.core.presentation.designsystem.theme.TaskyTheme

@Composable
fun SignInScreenRoot() {
    SignInScreen(
        onAction = { action ->
            when (action) {
                SignInScreenAction.OnLoginButtonClick -> {
                }

                SignInScreenAction.OnSignUpClick -> {}
            }
        }
    )
}

@Composable
fun SignInScreen(
    onAction: (SignInScreenAction) -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary),
        ) {
            Text(
                text = "Welcome Back!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )

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
                    TaskyTextField(
                        state = rememberTextFieldState(""),
                        keyboardType = KeyboardType.Email,
                        placeholder = stringResource(R.string.email_address),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    TaskyTextField(
                        state = rememberTextFieldState(""),
                        keyboardType = KeyboardType.Password,
                        placeholder = stringResource(R.string.password),
                        endIcon = PasswordHidden,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    TaskyActionButton(
                        text = stringResource(R.string.log_in),
                        isLoading = false,
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth(),
                        )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier
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

            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    TaskyTheme {
        SignInScreen(
            onAction = {}
        )
    }
}