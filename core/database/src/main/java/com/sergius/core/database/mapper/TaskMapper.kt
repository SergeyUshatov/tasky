package com.sergius.core.database.mapper

import com.sergius.core.database.entity.TaskEntity
import com.sergius.core.domain.dto.TaskDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun TaskDto.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id?: Uuid.random().toString(),
        title = this.title,
        description = this.description,
        isDone = this.isDone,
        time = this.time,
        remindAt = this.remindAt,
    )
}

fun TaskEntity.toTaskDto(): TaskDto {
    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.time,
        remindAt = this.remindAt,
        isDone = this.isDone,
        updatedAt = this.updatedAt
    )
}