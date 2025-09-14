package com.sergius.core.presentation.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

val AppLogo: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.tasky_logo)

val EyeClosed: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_closed)

val EyeOpened: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_opened)

val CheckIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.check_icon)

val DropdownIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.dropdown)

val CalendarTodayIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.calendar_today)

val CalendarAddItemIcon: ImageVector
    @Composable
    get() = Icons.Filled.Add

val ReminderIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.reminder_icon)

val TaskIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.task_icon)

val EventIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.event_icon)

val ChevronRightIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.chevron_right)

val BellIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.bell_icon)

val TaskDoneIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.task_done_icon)

val MoreIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.more)

val AddIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.plus)
