package com.sergius.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = Uuid.random().toString(),
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long,
    val time: Long,
    val isDone: Boolean,
)
