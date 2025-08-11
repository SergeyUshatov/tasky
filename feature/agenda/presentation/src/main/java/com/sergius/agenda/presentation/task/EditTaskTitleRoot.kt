package com.sergius.agenda.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergius.agenda.presentation.R
import com.sergius.core.presentation.designsystem.elements.TaskyDivider
import com.sergius.core.presentation.designsystem.elements.TaskyTextField
import com.sergius.core.presentation.designsystem.theme.TaskyTaskColor
import com.sergius.core.presentation.designsystem.theme.TaskyTheme

@Composable
fun EditTaskTitleRoot(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    state: TaskDetailsState,
) {

    EditTaskTitle(
        state = state.taskTitle,
        isFocused = state.isTitleFocused,
        onAction = { action ->
            when(action) {
                is TaskTitleAction.OnCancelClick -> {
                    onCancelClick()
                }
                is TaskTitleAction.OnSaveClick -> {
                    onSaveClick()
                }
                else -> Unit
            }
        }
    )
}

@Composable
private fun EditTaskTitle(
    state: TextFieldState,
    isFocused: Boolean,
    onAction: (TaskTitleAction) -> Unit
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            Header(onAction)
            TaskyDivider()

            TaskyTextField(
                state = state,
                placeholder = "title",
                isFocused = isFocused,
                onFocusChanged = { isFocused ->
                    onAction(TaskTitleAction.OnTitleFocusChanged(isFocused))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
            )
        }

    }
}

@Composable
private fun Header(onAction: (TaskTitleAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = stringResource(R.string.cancel),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .clickable {
                    onAction(TaskTitleAction.OnCancelClick)
                }
        )
        Text(
            text = stringResource(R.string.edit_title).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.save),
            style = MaterialTheme.typography.labelMedium,
            color = TaskyTaskColor,
            modifier = Modifier
                .clickable {
                    onAction(TaskTitleAction.OnSaveClick)
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditTaskTitlePreview(
) {
    TaskyTheme {
        EditTaskTitle(
            state = TextFieldState(),
            isFocused = false,
            onAction = {}
        )
    }
}