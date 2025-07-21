package com.sergius.core.presentation.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sergius.core.presentation.designsystem.R

val Inter = FontFamily(
    Font(
        resId = R.font.inter_light,
        weight = FontWeight.Companion.Light
    ),
    Font(
        resId = R.font.inter_regular,
        weight = FontWeight.Companion.Normal
    ),
    Font(
        resId = R.font.inter_medium,
        weight = FontWeight.Companion.Medium
    ),
    Font(
        resId = R.font.inter_semibold,
        weight = FontWeight.Companion.SemiBold
    ),
    Font(
        resId = R.font.inter_bold,
        weight = FontWeight.Companion.Bold
    ),
)

val LightTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Companion.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        color = TaskyBlack
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Companion.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp,
        color = TaskyLightOnSurfaceVariant
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Companion.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Companion.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Companion.SemiBold,
        fontStyle = FontStyle.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Companion.Bold,
        fontSize = 28.sp,
        lineHeight = 30.sp,
        color = TaskyLightOnBackground
    ),
)