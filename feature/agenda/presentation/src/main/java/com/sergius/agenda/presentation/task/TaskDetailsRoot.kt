@file:OptIn(ExperimentalMaterial3Api::class)

package com.sergius.agenda.presentation.task

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergius.agenda.presentation.R
import com.sergius.core.presentation.designsystem.BellIcon
import com.sergius.core.presentation.designsystem.elements.ChevronButton
import com.sergius.core.presentation.designsystem.elements.DatePickerModal
import com.sergius.core.presentation.designsystem.elements.DropdownIcon
import com.sergius.core.presentation.designsystem.elements.TaskyDivider
import com.sergius.core.presentation.designsystem.elements.TimePickerDialog
import com.sergius.core.presentation.designsystem.theme.TaskyRed
import com.sergius.core.presentation.designsystem.theme.TaskyTaskColor
import com.sergius.core.presentation.designsystem.theme.TaskyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskDetailsRoot(
    onAction: (TaskDetailsAction) -> Unit,
    state: TaskDetailsState,
) {
//    val state by viewModel.state.collectAsStateWithLifecycle()
    TaskDetails(
        state = state,
        onAction = onAction
//            { action ->
//            when (action) {
//                TaskDetailsAction.OnCancelClick -> {
//                    onCancelClick()
//                }
//
//                TaskDetailsAction.OnSaveClick -> {
//                    onSaveClick()
//                }
//
//                TaskDetailsAction.OnEditTitleClick -> {
////                    viewModel.onAction(TaskDetailsAction.OnEditTitleClick)
//                    onEditTitleClick()
//                }
//
//                else -> Unit //viewModel.onAction(action)
//            }
//        }
    )
}

@Composable
private fun TaskDetails(
    onAction: (TaskDetailsAction) -> Unit,
    state: TaskDetailsState
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Header(onAction)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                ItemType()
                TaskTitle(
                    title = state.taskTitle.text.toString(),
                    onAction = onAction
                )
                TaskyDivider()

                TaskDescription(onAction)
                TaskyDivider()

                TaskDateTime(
                    onAction = onAction,
                    showTimerDialog = state.showTimerDialog,
                    timePickerState = state.timePickerState,
                    showDateDialog = state.showDateDialog,
                    datePickerState = state.datePickerState
                )
                TaskyDivider()

                Reminder(
                    onAction = onAction,
                    items = state.reminderOptions,
                    selectedOption = state.reminderSelectedOption,
                    expanded = state.showReminderDropdown,
                )
            }

            TaskyDivider()
            Footer(onAction)
        }
    }
}

@Composable
private fun Reminder(
    onAction: (TaskDetailsAction) -> Unit,
    items: List<String>,
    selectedOption: String,
    expanded: Boolean,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clickable {
                    onAction(TaskDetailsAction.OnToggleReminderDropdownVisibility)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = BellIcon,
                contentDescription = "reminder icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f),
                text = selectedOption,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            val transition = updateTransition(targetState = expanded, label = "transition")
            val rotation by transition.animateFloat(label = "rotation") {
                if (it) 180f else 0f
            }
            DropdownIcon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .rotate(rotation)
            )
        }

        if (expanded) {
            val itemList = items.map {
                DropdownItem(
                    text = it,
                    onClick = {
                        onAction(TaskDetailsAction.OnDropdownItemClick(it))
                    }
                )
            }
            Row {
                Spacer(modifier = Modifier.weight(1f))
                DropdownList(
                    items = itemList,
                    selectedIndex = items.indexOf(selectedOption),
                    onDismissRequest = {
                        onAction(TaskDetailsAction.OnToggleReminderDropdownVisibility)
                    }
                )
            }
        }
    }
}

@Composable
private fun TaskDateTime(
    onAction: (TaskDetailsAction) -> Unit,
    showTimerDialog: Boolean,
    timePickerState: TimePickerState,
    showDateDialog: Boolean,
    datePickerState: DatePickerState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.at),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        TimePickerField(
            onAction = onAction,
            timePickerState = timePickerState,
            showTimerDialog = showTimerDialog,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp, horizontal = 4.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
        )

        DatePickerField(
            showModal = showDateDialog,
            onAction = onAction,
            datePickerState = datePickerState,
            modifier = Modifier
                .weight(1f)
                .background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
        )
    }
}

@Composable
private fun TimePickerField(
    onAction: (TaskDetailsAction) -> Unit,
    timePickerState: TimePickerState,
    showTimerDialog: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clickable { onAction(TaskDetailsAction.OnToggleTimerDialogVisibility) },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = timePickerState.toFormattedTime(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center
        )

        DropdownIcon()

        if (showTimerDialog) {
            TimePickerDialog(
                onDismiss = {
                    onAction(TaskDetailsAction.OnToggleTimerDialogVisibility)
                },
                onConfirm = {
                    onAction(TaskDetailsAction.OnToggleTimerDialogVisibility)
                }
            ) {
                TimePicker(
                    modifier = Modifier
                        .weight(1f),
                    state = timePickerState,
                )
            }
        }
    }
}

@Composable
private fun DatePickerField(
    onAction: (TaskDetailsAction) -> Unit,
    showModal: Boolean,
    datePickerState: DatePickerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
            .clickable {
                onAction(TaskDetailsAction.OnToggleDateDialogVisibility)
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = datePickerState.selectedDateMillis?.convertMillisToDate()
                ?: stringResource(R.string.date),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center
        )

        DropdownIcon()
    }

    if (showModal) {
        DatePickerModal(
            datePickerState = datePickerState,
            onDateSelected = { onAction(TaskDetailsAction.OnDateSelected(it)) },
            onDismiss = { onAction(TaskDetailsAction.OnToggleDateDialogVisibility) }
        )
    }
}

@Composable
private fun TaskDescription(onAction: (TaskDetailsAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = stringResource(R.string.task_description),
            style = MaterialTheme.typography.bodyMedium
        )
        ChevronButton(onClick = { onAction(TaskDetailsAction.OnEditDescriptionClick) })
    }
}

@Composable
private fun TaskTitle(
    onAction: (TaskDetailsAction) -> Unit,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .clickable {
                onAction(TaskDetailsAction.OnEditTitleClick)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(20.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = title.ifEmpty { stringResource(R.string.task_title) },
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        ChevronButton(onClick = { })
    }
}

@Composable
private fun Footer(
    onAction: (TaskDetailsAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.delete_task),
            style = MaterialTheme.typography.labelSmall,
            color = TaskyRed,
            modifier = Modifier
                .clickable {
                    onAction(TaskDetailsAction.OnDeleteClick)
                }
        )
    }
}

@Composable
private fun ItemType() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(20.dp)
                .background(
                    color = TaskyTaskColor,
                    shape = RoundedCornerShape(4.dp)
                )
        )

        Text(
            text = stringResource(R.string.task).toUpperCase(Locale.current),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Companion.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun Header(
    onAction: (TaskDetailsAction) -> Unit
) {
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
            modifier = Modifier
                .clickable {
                    onAction(TaskDetailsAction.OnCancelClick)
                }
        )
        Text(
            text = stringResource(R.string.edit_task),
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(R.string.save),
            style = MaterialTheme.typography.labelMedium,
            color = TaskyTaskColor,
            modifier = Modifier
                .clickable {
                    onAction(TaskDetailsAction.OnSaveClick)
                }
        )
    }
}

@Preview
@Composable
private fun TaskDetailsPreview() {
    val state = TaskDetailsState()
    TaskyTheme {
        TaskDetails(
            state = state,
            onAction = {},
        )
    }
}