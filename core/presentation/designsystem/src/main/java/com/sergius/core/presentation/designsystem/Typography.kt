package com.sergius.core.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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

val Typography = Typography(
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
        fontSize = 14.sp,
        lineHeight = 22.sp,
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
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Companion.SemiBold,
        fontSize = 24.sp,
        color = TaskyWhite
    ),
)