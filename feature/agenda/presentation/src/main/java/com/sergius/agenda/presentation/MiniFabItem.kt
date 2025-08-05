package com.sergius.agenda.presentation

import androidx.compose.ui.graphics.vector.ImageVector

data class MiniFabItem(
    val onClick: () -> Unit,
    val icon: ImageVector,
    val title: String,
)
