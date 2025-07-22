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
import com.sergius.auth.presentation.R
import com.sergius.auth.presentation.login.SignInScreenAction
import com.sergius.core.presentation.designsystem.CheckIcon
import com.sergius.core.presentation.designsystem.elements.TaskyActionButton
import com.sergius.core.presentation.designsystem.elements.TaskyPasswordField
import com.sergius.core.presentation.designsystem.elements.TaskyTextField
import com.sergius.core.presentation.designsystem.theme.TaskyCheckIconColor
import com.sergius.core.presentation.designsystem.theme.TaskyLightLink

@Composable
fun SignupScreenRoot() {
    SignupScreen()
}

@Composable
private fun SignupScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary),
        ) {
            Text(
                text = stringResource(R.string.create_your_account),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )
        }
    }
}


@Preview
@Composable
private fun SignupScreenPreview() {
    SignupScreen()
}

