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
    background = TaskyLightBackground,
    primary = TaskyLightPrimary,
    onPrimary = TaskyLightOnPrimary,
    onBackground = TaskyLightOnBackground,
    secondary = TaskyGreen,
    tertiary = TaskyTertiary,
    primaryContainer = TaskyLightPrimary,
    surface = TaskyLightSurface,
    onSurface = TaskyLightOnSurface,
    onSurfaceVariant = TaskyLightOnSurfaceVariant,
    surfaceContainerHigh = TaskyLightOnSurfaceHigh,
    error = TaskyRed,
    tertiaryContainer = TaskyLightPurple
)

val DarkColorScheme = darkColorScheme(
    background = TaskyDarkBackground,
    primary = TaskyDarkPrimary,
    onPrimary = TaskyDarkOnPrimary,
    onBackground = TaskyDarkOnBackground,
    surface = TaskyDarkSurface,
    onSurface = TaskyDarkOnSurface,
    onSurfaceVariant = TaskyDarkOnSurfaceVariant,
    surfaceContainerHigh = TaskyDarkOnSurfaceHigh,
    secondary = TaskyGreen,
    tertiary = TaskyLightGreen,
    error = TaskyDarkRed,
    tertiaryContainer = TaskyDarkPurple
)

@Composable
fun TaskyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = if (darkTheme) DarkTypography else LightTypography
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}