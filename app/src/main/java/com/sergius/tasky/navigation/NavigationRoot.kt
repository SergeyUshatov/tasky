package com.sergius.tasky.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.sergius.agenda.presentation.agendaoverview.AgendaScreenRoot
import com.sergius.agenda.presentation.event.EventDetailsRoot
import com.sergius.agenda.presentation.reminder.ReminderDetailsRoot
import com.sergius.agenda.presentation.task.TaskDetailsRoot
import com.sergius.auth.presentation.login.SignInScreenRoot
import com.sergius.auth.presentation.signup.SignupScreenRoot

@Composable
fun NavigationRoot(
    isLoggedIn: Boolean
) {
    val initialNavKey = if (isLoggedIn) AgendaNavKey else AuthorizeNavKey
    val backStack = rememberNavBackStack(initialNavKey)
    val context = LocalContext.current
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
                            onCancelClick = {
                                backStack.clear()
                                backStack.add(AgendaNavKey)
                            },
                            onSaveClick = {
                                backStack.clear()
                                backStack.add(AgendaNavKey)
                            },
                        )
                    }

                    is EventNavKey -> {
                        EventDetailsRoot()
                    }

                    is ReminderNavKey -> {
                        ReminderDetailsRoot()
                    }
                    else -> {
                        Toast.makeText(context, "Unknown navigation key: $key", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
}