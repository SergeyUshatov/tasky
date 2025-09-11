package com.sergius.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val from: Long,
    val to: Long,
    val attendeeIds: List<String>,
    val photoKeys: List<String>
)
