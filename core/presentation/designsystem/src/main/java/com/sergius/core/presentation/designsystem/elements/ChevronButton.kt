package com.sergius.core.presentation.designsystem.elements

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.sergius.core.presentation.designsystem.ChevronRightIcon

@Composable
fun ChevronButton(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = ChevronRightIcon,
            contentDescription = "chevron",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ChevronIcon() {
    Icon(
        imageVector = ChevronRightIcon,
        contentDescription = "chevron",
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
}