package com.sergius.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = Uuid.random().toString(),
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long,
    val from: Long,
    val to: Long,
    val attendeeIds: List<String>,
    val photoKeys: List<String>
)
