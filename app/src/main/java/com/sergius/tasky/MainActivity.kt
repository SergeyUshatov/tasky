package com.sergius.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergius.core.presentation.designsystem.theme.TaskyTheme
import com.sergius.tasky.navigation.NavigationRoot
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state = viewModel.state.collectAsStateWithLifecycle()
            installSplashScreen().apply {
                setKeepOnScreenCondition {
                    state.value.isCheckingAuth
                }
            }
            TaskyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!state.value.isCheckingAuth) {
                        NavigationRoot(
                            isLoggedIn = state.value.isLoggedIn
                        )
                    }
                }
            }
        }
    }
}