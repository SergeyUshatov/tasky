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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergius.agenda.presentation.R.string.cancel
import com.sergius.agenda.presentation.R.string.edit_screen
import com.sergius.agenda.presentation.R.string.save
import com.sergius.agenda.presentation.task.EditTextAction
import com.sergius.agenda.presentation.task.EditTextAction.OnSaveClick
import com.sergius.core.domain.TextType
import com.sergius.core.presentation.designsystem.elements.TaskyDivider
import com.sergius.core.presentation.designsystem.theme.TaskyTaskColor
import com.sergius.core.presentation.designsystem.theme.TaskyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditTextScreenRoot(
    textType: TextType,
    onSaveClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    viewModel: EditTextScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditTextScreen(
        textType = textType,
        state =  state,
        onAction = { action ->
            when(action) {
                is EditTextAction.OnCancelClick -> {
                    onCancelClick()
                }
                is EditTextAction.OnSaveClick -> {
                    onSaveClick(action.text)
                }
            }
        }
    )
}

@Composable
private fun EditTextScreen(
    textType: TextType,
    state: TextFieldState,
    onAction: (EditTextAction) -> Unit
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(cancel),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clickable {
                            onAction(EditTextAction.OnCancelClick)
                        }
                )
                Text(
                    text = stringResource(edit_screen, textType.name).uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(save),
                    style = MaterialTheme.typography.labelMedium,
                    color = TaskyTaskColor,
                    modifier = Modifier
                        .clickable {
                            onAction(
                                OnSaveClick(
                                    text = state.text.toString()
                                )
                            )
                        }
                )
            }

            TaskyDivider()
            BasicTextField(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
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
            onAction = {}
        )
    }
}