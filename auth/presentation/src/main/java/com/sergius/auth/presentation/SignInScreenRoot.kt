package com.sergius.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sergius.core.presentation.designsystem.TaskyTheme

@Composable
fun SignInScreenRoot() {
    SignInScreen(
        onAction = { action ->
            when (action) {
                SignInScreenAction.OnLoginButtonClick -> {

                }
            }
        }
    )
}

@Composable
fun SignInScreen(
    onAction: (SignInScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Text(
            text = "Welcome Back!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
        )
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