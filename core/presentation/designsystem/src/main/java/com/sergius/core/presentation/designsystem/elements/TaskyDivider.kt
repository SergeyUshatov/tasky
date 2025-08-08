package com.sergius.core.presentation.designsystem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskyDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        thickness = 1.dp,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 12.dp)
    )
}