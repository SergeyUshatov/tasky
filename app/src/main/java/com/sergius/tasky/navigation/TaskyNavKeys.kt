package com.sergius.tasky.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.NavKey
import com.sergius.agenda.presentation.task.TaskDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Contextual
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

@Serializable
//data class TaskTitleEditNavKey(@Contextual val state: MutableStateFlow<TaskDetailsState>) : NavKey
data object TaskTitleEditNavKey : NavKey
