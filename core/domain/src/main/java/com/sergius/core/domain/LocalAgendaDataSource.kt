package com.sergius.core.domain

import com.sergius.core.domain.dto.EventDto
import com.sergius.core.domain.dto.ReminderDto
import com.sergius.core.domain.dto.TaskDto
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result

typealias TaskId = String
interface LocalAgendaDataSource {
    suspend fun upsertTask(taskDto: TaskDto): Result<TaskId, DataError.Local>
    suspend fun getTasks(): List<TaskDto>
    fun upsertEvent(eventDto: EventDto)
    fun upsertReminder(reminderDto: ReminderDto)
}