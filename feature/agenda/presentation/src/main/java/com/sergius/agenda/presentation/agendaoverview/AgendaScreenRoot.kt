package com.sergius.agenda.presentation.agendaoverview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergius.core.presentation.designsystem.CalendarAddItemIcon
import com.sergius.core.presentation.designsystem.CalendarTodayIcon
import com.sergius.core.presentation.designsystem.DropdownIcon
import com.sergius.core.presentation.designsystem.EventIcon
import com.sergius.core.presentation.designsystem.ReminderIcon
import com.sergius.core.presentation.designsystem.TaskIcon
import com.sergius.core.presentation.designsystem.theme.TaskyCalendarSupplementary
import com.sergius.core.presentation.designsystem.theme.TaskyTheme
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun AgendaScreenRoot(
    onTaskCreateClick: () -> Unit,
    onEventCreateClick: () -> Unit,
    onReminderCreateClick: () -> Unit,
    viewModel: AgendaViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AgendaScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AgendaAction.OnTaskCreateClick -> {
                    viewModel.onAction(AgendaAction.OnCreateAgendaItemClick)
                    onTaskCreateClick()
                }

                is AgendaAction.OnEventCreateClick -> {
                    viewModel.onAction(AgendaAction.OnCreateAgendaItemClick)
                    onEventCreateClick()
                }

                is AgendaAction.OnReminderCreateClick -> {
                    viewModel.onAction(AgendaAction.OnCreateAgendaItemClick)
                    onReminderCreateClick()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun AgendaScreen(
    onAction: (AgendaAction) -> Unit,
    state: AgendaState
) {
    Scaffold(
        floatingActionButton = {
            FabUi(
                onAction = onAction,
                expanded = state.fabExpanded
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            AgendaHeader(month = state.month)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                CalendarDays(calendarDays = state.days, onClick = onAction)

                Text(
                    text = "Today",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

@Composable
private fun FabUi(
    onAction: (AgendaAction) -> Unit,
    expanded: Boolean
) {
    val items = listOf(
        MiniFabItem(
            icon = TaskIcon, title = "Task",
            onClick = { onAction(AgendaAction.OnTaskCreateClick) }
        ),
        MiniFabItem(
            icon = EventIcon, title = "Event",
            onClick = { onAction(AgendaAction.OnEventCreateClick) }
        ),
        MiniFabItem(
            icon = ReminderIcon, title = "Reminder",
            onClick = { onAction(AgendaAction.OnReminderCreateClick) }
        )
    )

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(Modifier.padding(bottom = 8.dp)) {
                items(items) {
                    ItemUi(
                        icon = it.icon,
                        title = it.title,
                        onClick = it.onClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = { onAction(AgendaAction.OnCreateAgendaItemClick) },
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.rotate(rotation)
        ) {
            Icon(
                imageVector = CalendarAddItemIcon, contentDescription = "Add Agenda Item",
            )
        }
    }
}

@Composable
private fun ItemUi(
    onClick: () -> Unit,
    icon: ImageVector,
    title: String
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
                .padding(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(48.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(imageVector = icon, contentDescription = "")
        }
    }
}

@Composable
private fun CalendarDays(
    onClick: (AgendaAction) -> Unit,
    calendarDays: List<CalendarUi>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(calendarDays) { calendarDay ->
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
                    .clickable {
                        onClick(AgendaAction.OnDayClick(calendarDay))
                    }
                    .background(color = TaskyCalendarSupplementary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = calendarDay.dayOfWeek,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "${calendarDay.day}",
                    modifier = Modifier
                        .padding(bottom = 4.dp, start = 8.dp, end = 8.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun AgendaHeader(
    month: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MonthHeaderItem(month = month)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                imageVector = CalendarTodayIcon,
                contentDescription = "Dropdown Icon",
                tint = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(shape = CircleShape)
                    .background(color = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(
                    text = "AB",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
private fun MonthHeaderItem(
    month: String
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = month,
            style = MaterialTheme.typography.labelMedium,
        )

        Icon(
            imageVector = DropdownIcon,
            contentDescription = "Dropdown Icon",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun AgendaScreenPreview() {
    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val startDay = now.minus(15, DateTimeUnit.DAY)
    val endDay = startDay.plus(30, DateTimeUnit.DAY)

    val calendarDays = (startDay..endDay).map { date ->
        CalendarUi(
            month = date.month,
            day = date.day,
            dayOfWeek = date.dayOfWeek.name.take(1)
        )
    }
    val state = AgendaState(
        fabExpanded = true,
        month = Month.AUGUST.name,
        days = calendarDays
    )

    TaskyTheme {
        AgendaScreen(
            state = state,
            onAction = {}
        )
    }
}