package com.sergius.agenda.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
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
import com.sergius.agenda.presentation.task.EditTextAction
import com.sergius.core.domain.TextType
import com.sergius.core.presentation.designsystem.elements.TaskyDivider
import com.sergius.core.presentation.designsystem.elements.TaskyTextField
import com.sergius.core.presentation.designsystem.theme.TaskyTaskColor
import com.sergius.core.presentation.designsystem.theme.TaskyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditTextScreenRoot(
    textType: TextType,
    fieldState: TextFieldState,
    isFocused: Boolean,
    initialText: String,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    EditTextScreen(
        textType = textType,
        state =  fieldState,
        isFocused = isFocused,
        onAction = { action ->
            when(action) {
                is EditTextAction.OnCancelClick -> {
                    fieldState.clearText()
                    fieldState.edit {
                        append(initialText)
                    }
                    onCancelClick()
                }
                is EditTextAction.OnSaveClick -> {
                    onSaveClick()
                }

                else -> Unit
            }
        }
    )
}

@Composable
private fun EditTextScreen(
    textType: TextType,
    state: TextFieldState,
    isFocused: Boolean,
    onAction: (EditTextAction) -> Unit
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
                placeholder = textType.name,
                isFocused = isFocused,
                onFocusChanged = { isFocused ->
                    onAction(EditTextAction.OnFieldFocusChanged(isFocused))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun Header(onAction: (EditTextAction) -> Unit) {
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
                    onAction(EditTextAction.OnCancelClick)
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
                    onAction(EditTextAction.OnSaveClick)
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
        EditTextScreen(
            textType = TextType.TITLE,
            state = TextFieldState(),
            isFocused = false,
            onAction = {}
        )
    }
}