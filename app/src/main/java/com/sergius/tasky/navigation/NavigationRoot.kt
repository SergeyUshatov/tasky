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
import com.sergius.auth.presentation.login.SignInScreenRoot
import com.sergius.auth.presentation.signup.SignupScreenRoot

@Composable
fun NavigationRoot() {
    val initialNavKey = AuthorizeNavKey
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
            when (key) {
                is AuthorizeNavKey -> {
                    NavEntry(key = key) {
                        SignInScreenRoot(
                            onSignUpClick = {
                                backStack.clear()
                                backStack.add(SignupNavKey)
                            }
                        )
                    }
                }

                is SignupNavKey -> {
                    NavEntry(key = key) {
                        SignupScreenRoot(
                            onLoginClick = {
                                backStack.clear()
                                backStack.add(AuthorizeNavKey)
                            }
                        )
                    }
                }

                else -> {
                    Toast.makeText(context,"Unknown navigation key: $key", Toast.LENGTH_SHORT).show()
                    NavEntry(key = key) {}
                }
            }
        }
    )
}