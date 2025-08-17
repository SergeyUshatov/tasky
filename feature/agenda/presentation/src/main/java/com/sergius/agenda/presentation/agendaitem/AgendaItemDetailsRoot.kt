@file:OptIn(ExperimentalMaterial3Api::class)

package com.sergius.agenda.presentation.agendaitem

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergius.agenda.presentation.R
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.presentation.designsystem.BellIcon
import com.sergius.core.presentation.designsystem.elements.ChevronIcon
import com.sergius.core.presentation.designsystem.elements.DatePickerModal
import com.sergius.core.presentation.designsystem.elements.DropdownIcon
import com.sergius.core.presentation.designsystem.elements.DropdownItem
import com.sergius.core.presentation.designsystem.elements.DropdownList
import com.sergius.core.presentation.designsystem.elements.TaskyDivider
import com.sergius.core.presentation.designsystem.elements.TimePickerDialog
import com.sergius.core.presentation.designsystem.theme.TaskyEventColor
import com.sergius.core.presentation.designsystem.theme.TaskyRed
import com.sergius.core.presentation.designsystem.theme.TaskyReminderColor
import com.sergius.core.presentation.designsystem.theme.TaskyTaskColor
import com.sergius.core.presentation.designsystem.theme.TaskyTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgendaItemDetailsRoot(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onEditTitleClick: (String) -> Unit,
    onEditDescriptionClick: (String) -> Unit,
    itemType: AgendaItemType,
    title: String? = null,
    description: String? = null,
    viewModel: AgendaItemDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(title) {
        title?.let {
            viewModel.updateTitle(it)
        }
    }

    LaunchedEffect(description) {
        description?.let {
            viewModel.updateDescription(it)
        }
    }

    TaskDetails(
        itemType = itemType,
        state = state,
        onAction =
            { action ->
                when (action) {
                    AgendaItemDetailsAction.OnCancelClick -> {
                        onCancelClick()
                    }

                    is AgendaItemDetailsAction.OnSaveClick -> {
                        onSaveClick()
                    }

                    AgendaItemDetailsAction.OnEditTitleClick -> {
                        onEditTitleClick(state.title)
                    }

                    AgendaItemDetailsAction.OnEditDescriptionClick -> {
                        onEditDescriptionClick(state.description)
                    }

                    else -> Unit
                }

                viewModel.onAction(action)
            }
    )
}

@Composable
private fun TaskDetails(
    onAction: (AgendaItemDetailsAction) -> Unit,
    state: AgendaItemDetailsState,
    itemType: AgendaItemType,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Header(onAction = onAction, itemType = itemType)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                ItemType(itemType)
                ItemTitle(
                    title = state.title,
                    itemType = itemType.capitalize(),
                    onAction = onAction
                )
                TaskyDivider()

                ItemDescription(
                    description = state.description,
                    onAction = onAction,
                    itemType = itemType.capitalize(),
                )
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
            Footer(onAction, itemType)
        }
    }
}

@Composable
private fun Reminder(
    onAction: (AgendaItemDetailsAction) -> Unit,
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
                    onAction(AgendaItemDetailsAction.OnToggleReminderDropdownVisibility)
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
                        onAction(AgendaItemDetailsAction.OnDropdownItemClick(it))
                    }
                )
            }
            Row {
                Spacer(modifier = Modifier.weight(1f))
                DropdownList(
                    items = itemList,
                    selectedIndex = items.indexOf(selectedOption),
                    onDismissRequest = {
                        onAction(AgendaItemDetailsAction.OnToggleReminderDropdownVisibility)
                    }
                )
            }
        }
    }
}

@Composable
private fun TaskDateTime(
    onAction: (AgendaItemDetailsAction) -> Unit,
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
    onAction: (AgendaItemDetailsAction) -> Unit,
    timePickerState: TimePickerState,
    showTimerDialog: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clickable { onAction(AgendaItemDetailsAction.OnToggleTimerDialogVisibility) },
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
                    onAction(AgendaItemDetailsAction.OnToggleTimerDialogVisibility)
                },
                onConfirm = {
                    onAction(AgendaItemDetailsAction.OnToggleTimerDialogVisibility)
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
    onAction: (AgendaItemDetailsAction) -> Unit,
    showModal: Boolean,
    datePickerState: DatePickerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
            .clickable {
                onAction(AgendaItemDetailsAction.OnToggleDateDialogVisibility)
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
            onDateSelected = { onAction(AgendaItemDetailsAction.OnDateSelected(it)) },
            onDismiss = { onAction(AgendaItemDetailsAction.OnToggleDateDialogVisibility) }
        )
    }
}

@Composable
private fun ItemDescription(
    onAction: (AgendaItemDetailsAction) -> Unit,
    description: String,
    itemType: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .clickable {
                onAction(AgendaItemDetailsAction.OnEditDescriptionClick)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = description.ifEmpty { stringResource(R.string.item_description, itemType) },
            style = MaterialTheme.typography.bodyMedium
        )
        ChevronIcon()
    }
}

@Composable
private fun ItemTitle(
    onAction: (AgendaItemDetailsAction) -> Unit,
    title: String,
    itemType: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .clickable {
                onAction(AgendaItemDetailsAction.OnEditTitleClick)
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
            text = title.ifEmpty { stringResource(R.string.item_title, itemType) },
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        ChevronIcon()
    }
}

@Composable
private fun Footer(
    onAction: (AgendaItemDetailsAction) -> Unit,
    itemType: AgendaItemType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.delete_item, itemType.name),
            style = MaterialTheme.typography.labelSmall,
            color = TaskyRed,
            modifier = Modifier
                .clickable {
                    onAction(AgendaItemDetailsAction.OnDeleteClick)
                }
        )
    }
}

@Composable
private fun ItemType(
    itemType: AgendaItemType
) {
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
                    color = when (itemType) {
                        AgendaItemType.TASK -> TaskyTaskColor
                        AgendaItemType.EVENT -> TaskyEventColor
                        AgendaItemType.REMINDER -> TaskyReminderColor
                    },
                    shape = RoundedCornerShape(4.dp)
                )
        )

        Text(
            text = itemType.name,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Companion.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun Header(
    onAction: (AgendaItemDetailsAction) -> Unit,
    itemType: AgendaItemType
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
                    onAction(AgendaItemDetailsAction.OnCancelClick)
                }
        )
        Text(
            text = stringResource(R.string.edit_item, itemType.name),
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(R.string.save),
            style = MaterialTheme.typography.labelMedium,
            color = TaskyTaskColor,
            modifier = Modifier
                .clickable {
                    onAction(AgendaItemDetailsAction.OnSaveClick(itemType))
                }
        )
    }
}

@Preview
@Composable
private fun TaskDetailsPreview() {
    val state = AgendaItemDetailsState()
    TaskyTheme {
        TaskDetails(
            state = state,
            onAction = {},
            itemType = AgendaItemType.TASK,
        )
    }
}