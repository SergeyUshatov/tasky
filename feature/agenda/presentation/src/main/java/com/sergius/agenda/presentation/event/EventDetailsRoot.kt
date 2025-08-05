package com.sergius.agenda.presentation.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sergius.core.presentation.designsystem.theme.TaskyTheme

@Composable
fun EventDetailsRoot() {
    EventDetails()
}

@Composable
private fun EventDetails() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = "Event Details will be here")
        }
    }
}

@Preview
@Composable
private fun EventDetailsPreview() {
    TaskyTheme {
        EventDetails()
    }
}