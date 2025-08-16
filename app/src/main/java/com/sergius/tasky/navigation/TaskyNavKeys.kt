package com.sergius.tasky.navigation

import androidx.navigation3.runtime.NavKey
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.TextType
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
    val fieldText: String
) : NavKey

@Serializable
data class AgendaItemDetailNavKey(
    val itemType: AgendaItemType,
    val titleText: String? = null,
    val descriptionText: String? = null,
) : NavKey