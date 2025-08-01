package com.sergius.agenda.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergius.core.presentation.designsystem.CalendarTodayIcon
import com.sergius.core.presentation.designsystem.DropdownIcon
import com.sergius.core.presentation.designsystem.theme.TaskyTheme

@Composable
fun AgendaScreenRoot() {
    AgendaScreen()
}

@Composable
fun AgendaScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MonthItem()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Icon(
                        imageVector = CalendarTodayIcon,
                        contentDescription = "Dropdown Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(shape = CircleShape)
                            .background(color = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text(
                            text = "AB",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Text(text = "asd")
            }
        }
    }
}

@Composable
private fun MonthItem() {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Month",
            style = MaterialTheme.typography.labelMedium,
        )

        Icon(
            imageVector = DropdownIcon,
            contentDescription = "Dropdown Icon",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun AgendaScreenPreview() {
    TaskyTheme {
        AgendaScreen()
    }
}