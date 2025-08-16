package com.sergius.agenda.presentation

import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.TextType

@Composable
fun AgendaItemType.capitalize() = this.name
    .lowercase()
    .capitalize(Locale.current)

@Composable
fun TextType.capitalize() = this.name
    .lowercase()
    .capitalize(Locale.current)

@Composable
fun TextType.lineLimits() =
    if (this == TextType.TITLE) TextFieldLineLimits.SingleLine
    else TextFieldLineLimits.Default