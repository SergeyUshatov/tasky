package com.sergius.core.presentation.designsystem.elements

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergius.core.presentation.designsystem.DropdownIcon

@Composable
fun DropdownIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = DropdownIcon,
        contentDescription = "Dropdown Icon",
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}