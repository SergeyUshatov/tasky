package com.sergius.agenda.presentation.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sergius.core.presentation.designsystem.theme.TaskyTheme

@Composable
fun ReminderDetailsRoot() {
    ReminderDetails()
}

@Composable
private fun ReminderDetails() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Reminder Details will be here")
        }
    }
}

@Preview
@Composable
private fun ReminderDetailsPreview() {
    TaskyTheme {
        ReminderDetails()
    }
}