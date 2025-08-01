package com.sergius.core.presentation.designsystem

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
