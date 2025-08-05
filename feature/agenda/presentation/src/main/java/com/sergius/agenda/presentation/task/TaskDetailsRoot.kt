package com.sergius.agenda.presentation.task

import androidx.compose.foundation.layout.Box
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
fun TaskDetailsRoot() {
    TaskDetails()
}

@Composable
private fun TaskDetails() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = "Task Details will be here")
        }
    }
}

@Preview
@Composable
private fun TaskDetailsPreview() {
    TaskyTheme {
        TaskDetails()
    }
}