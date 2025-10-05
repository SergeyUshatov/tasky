package com.sergius.agenda.data.mappers

import com.sergius.agenda.data.dto.CreateTaskRequest
import com.sergius.agenda.data.dto.TaskResponse
import com.sergius.core.domain.model.Task
import java.time.Instant

fun Task.toCreateTaskRequest() = CreateTaskRequest(
    id = id,
    title = title,
    description = description,
    time = Instant.ofEpochMilli(time).toString(),
    remindAt = Instant.ofEpochMilli(remindAt).toString(),
    updatedAt = Instant.ofEpochMilli(updatedAt ?: 0).toString(),
    isDone = isDone,
)

fun TaskResponse.toTask() = Task(
    id = id,
    title = title,
    description = description,
    time = time.toLong(),
    remindAt = remindAt.toLong(),
    isDone = isDone,
)