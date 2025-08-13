package com.sergius.tasky.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.sergius.agenda.presentation.agendaoverview.AgendaScreenRoot
import com.sergius.agenda.presentation.event.EventDetailsRoot
import com.sergius.agenda.presentation.reminder.ReminderDetailsRoot
import com.sergius.agenda.presentation.task.EditTaskTitleRoot
import com.sergius.agenda.presentation.task.TaskDetailsAction
import com.sergius.agenda.presentation.task.TaskDetailsRoot
import com.sergius.agenda.presentation.task.TaskDetailsViewModel
import com.sergius.agenda.presentation.task.TaskTitleAction
import com.sergius.auth.presentation.login.SignInScreenRoot
import com.sergius.auth.presentation.signup.SignupScreenRoot

@Composable
fun NavigationRoot(
    isLoggedIn: Boolean
) {
    val initialNavKey = if (isLoggedIn) AgendaNavKey else AuthorizeNavKey
    val backStack = rememberNavBackStack(initialNavKey)
    val context = LocalContext.current
    val taskDetailsViewModel: TaskDetailsViewModel = viewModel()
    val taskState by taskDetailsViewModel.state.collectAsStateWithLifecycle()
    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            NavEntry(key = key) {
                when (key) {
                    is AuthorizeNavKey -> {
                        SignInScreenRoot(
                            onSignUpClick = {
                                backStack.clear()
                                backStack.add(SignupNavKey)
                            },
                            onSignInSuccess = {
                                backStack.clear()
                                backStack.add(AgendaNavKey)
                            }
                        )
                    }

                    is SignupNavKey -> {
                        SignupScreenRoot(
                            onLoginClick = {
                                backStack.clear()
                                backStack.add(AuthorizeNavKey)
                            },
                            onSignupSuccess = {
                                backStack.clear()
                                backStack.add(AuthorizeNavKey)
                            }
                        )
                    }

                    is AgendaNavKey -> {
                        AgendaScreenRoot(
                            onTaskCreateClick = {
                                backStack.add(TaskNavKey)
                            },
                            onEventCreateClick = {
                                backStack.add(EventNavKey)
                            },
                            onReminderCreateClick = {
                                backStack.add(ReminderNavKey)
                            },
                        )
                    }

                    is TaskNavKey -> {
                        TaskDetailsRoot(
                            state = taskState,
                            onAction = { action ->
                                when (action) {
                                    TaskDetailsAction.OnCancelClick -> {
                                        backStack.clear()
                                        backStack.add(AgendaNavKey)
                                    }

                                    TaskDetailsAction.OnSaveClick -> {
                                        backStack.clear()
                                        backStack.add(AgendaNavKey)
                                    }

                                    TaskDetailsAction.OnEditTitleClick -> {
                                        backStack.add(TaskTitleEditNavKey)
                                    }

                                    else -> taskDetailsViewModel.onAction(action)
                                }
                            }
                        )
                    }

                    is EventNavKey -> {
                        EventDetailsRoot()
                    }

                    is ReminderNavKey -> {
                        ReminderDetailsRoot()
                    }

                    is TaskTitleEditNavKey -> {
                        EditTaskTitleRoot(
                            state = taskState,
                            onCancelClick = {
                                taskDetailsViewModel.onAction(TaskTitleAction.OnCancelClick)
                                backStack.removeAll { key == it }
                            },
                            onSaveClick = {
                                backStack.removeAll { key == it }
                            }
                        )
                    }

                    else -> {
                        Toast.makeText(context, "Unknown navigation key: $key", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    )
}