package com.sergius.tasky.navigation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.navigation3.runtime.NavKey
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.TextType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data object AuthorizeNavKey : NavKey

@Serializable
data object SignupNavKey : NavKey

@Serializable
data object AgendaNavKey : NavKey

@Serializable
data class TextEditNavKey(
    val itemType: AgendaItemType,
    val textType: TextType,
    val isFocused: Boolean,
    val initialText: String,
    @Contextual val fieldState: TextFieldState
) : NavKey

@Serializable
data class AgendaItemDetailNavKey(
    val itemType: AgendaItemType,
    @Contextual val titleState: TextFieldState? = null,
    @Contextual val descriptionState: TextFieldState? = null,
) : NavKey