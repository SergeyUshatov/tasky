package com.sergius.agenda.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.sergius.core.presentation.designsystem.CheckIcon
import com.sergius.core.presentation.designsystem.theme.TaskyCheckIconColor

data class DropdownItem(
    val text: String,
    val modifier: Modifier = Modifier,
    val onClick: () -> Unit
)

@Composable
fun DropdownList(
    items: List<DropdownItem>,
    selectedIndex: Int,
    onDismissRequest: () -> Unit,
) {
    Box {
        val configuration = LocalConfiguration.current
        val popupWidth = configuration.screenWidthDp.dp * 2 / 3
        val popupHeight = 40.dp * items.size

        Popup(
            alignment = Alignment.TopEnd,
            onDismissRequest = onDismissRequest,
            offset = IntOffset(-60, 0),
        ) {
            ElevatedCard(
                modifier = Modifier
                    .size(popupWidth, popupHeight),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.onPrimary),
                    horizontalAlignment = Alignment.Start,
                ) {
                    itemsIndexed(items) { index, item ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .clickable {
                                    item.onClick()
                                },
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = item.text,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (index == selectedIndex) {
                                Icon(
                                    imageVector = CheckIcon,
                                    contentDescription = "selected reminder",
                                    tint = TaskyCheckIconColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}