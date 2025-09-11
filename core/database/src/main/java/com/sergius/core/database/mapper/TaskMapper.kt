package com.sergius.core.database.mapper

import com.sergius.core.database.entity.TaskEntity
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.model.AgendaItem
import com.sergius.core.domain.model.Task

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isDone = this.isDone,
        time = this.time,
        remindAt = this.remindAt,
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.time,
        remindAt = this.remindAt,
        isDone = this.isDone,
        updatedAt = this.updatedAt
    )
}

fun Task.toAgendaItem(): AgendaItem {
    return AgendaItem(
        id = this.id,
        title = this.title,
        description = this.description,
        isDone = this.isDone,
        time = this.time,
        remindAt = this.remindAt,
        itemType = AgendaItemType.TASK
    )
}