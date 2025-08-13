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
import com.sergius.agenda.presentation.EditTextScreenRoot
import com.sergius.agenda.presentation.agendaoverview.AgendaScreenRoot
import com.sergius.agenda.presentation.task.TaskDetailsRoot
import com.sergius.auth.presentation.login.SignInScreenRoot
import com.sergius.auth.presentation.signup.SignupScreenRoot
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.TextType
import timber.log.Timber

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
                                backStack.add(AgendaItemDetailNavKey(AgendaItemType.TASK))
                            },
                            onEventCreateClick = {
                                backStack.add(AgendaItemDetailNavKey(AgendaItemType.EVENT))
                            },
                            onReminderCreateClick = {
                                backStack.add(AgendaItemDetailNavKey(AgendaItemType.REMINDER))
                            },
                        )
                    }

                    is AgendaItemDetailNavKey -> {
                        when (key.itemType) {
                            AgendaItemType.TASK -> {
                                TaskDetailsRoot(
                                    onCancelClick = {
                                        backStack.clear()
                                        backStack.add(AgendaNavKey)
                                    },
                                    onSaveClick = {
                                        backStack.clear()
                                        backStack.add(AgendaNavKey)
                                    },
                                    onEditTitleClick = { state, isFocused ->
                                        backStack.add(
                                            TextEditNavKey(
                                                itemType = key.itemType,
                                                textType = TextType.TITLE,
                                                fieldState = state,
                                                isFocused = isFocused,
                                                initialText = state.text.toString()
                                            )
                                        )
                                        key.titleState ?: state
                                    }
                                )
                            }

                            AgendaItemType.EVENT -> {}
                            AgendaItemType.REMINDER -> {}
                        }
                    }

                    is TextEditNavKey -> {
                        EditTextScreenRoot(
                            textType = key.textType,
                            fieldState = key.fieldState,
                            isFocused = key.isFocused,
                            initialText = key.initialText,
                            onCancelClick = {
                                backStack.removeLastOrNull()
                                            },
                            onSaveClick = {
                                // Navigate back to AgendaItemDetailNavKey with the result
                                val currentAgendaDetail =
                                    backStack.findLast { it is AgendaItemDetailNavKey } as? AgendaItemDetailNavKey
                                backStack.removeLastOrNull() // Remove current TextEditNavKey
                                backStack.removeLastOrNull() // Remove current AgendaItemDetailNavKey

                                when (key.textType) {
                                    TextType.TITLE -> backStack.add(
                                        AgendaItemDetailNavKey(
                                            itemType = key.itemType,
                                            titleState = currentAgendaDetail?.titleState,
                                        )
                                    )

                                    TextType.DESCRIPTION -> backStack.add(
                                        AgendaItemDetailNavKey(
                                            itemType = key.itemType,
                                            descriptionState = currentAgendaDetail?.descriptionState
                                        )
                                    )
                                }
                            }
                        )
                    }

                    else -> {
                        Timber.e("Unknown navigation key: $key")
                        Toast.makeText(context, "Unknown navigation key: $key", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    )
}