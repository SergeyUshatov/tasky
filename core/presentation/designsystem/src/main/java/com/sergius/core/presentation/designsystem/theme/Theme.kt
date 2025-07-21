package com.sergius.core.presentation.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LightColorScheme = lightColorScheme(
    primary = TaskyLightPrimary,
    onPrimary = TaskyLightOnPrimary,
    background = TaskyBlack,
    onBackground = TaskyLightOnBackground,
    secondary = TaskyGreen,
    tertiary = TaskyLightGreen,
    primaryContainer = TaskyLightPrimary,
    surface = TaskyLightSurface,
    onSurface = TaskyLightOnSurface,
    onSurfaceVariant = TaskyLightOnSurfaceVariant,
    error = TaskyRed,
    tertiaryContainer = TaskyLightPurple
)

val DarkColorScheme = darkColorScheme(
    primary = TaskyBlack,
    background = TaskyBlack,
    secondary = TaskyGreen,
    tertiary = TaskyLightGreen,
    secondaryContainer = TaskyBlack10,
    onSurface = TaskyLightPrimary,
    error = TaskyDarkRed,
    tertiaryContainer = TaskyDarkPurple
)

@Composable
fun TaskyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LightTypography,
        content = content
    )
}