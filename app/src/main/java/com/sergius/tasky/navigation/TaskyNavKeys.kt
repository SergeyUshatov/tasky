package com.sergius.tasky.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object AuthorizeNavKey : NavKey

@Serializable
data object SignupNavKey : NavKey

@Serializable
data object AgendaNavKey : NavKey

@Serializable
data object TaskNavKey : NavKey

@Serializable
data object EventNavKey : NavKey

@Serializable
data object ReminderNavKey : NavKey
