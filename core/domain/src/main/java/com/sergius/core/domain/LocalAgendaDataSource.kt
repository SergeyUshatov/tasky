package com.sergius.core.domain

import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result

typealias TaskId = String
interface LocalAgendaDataSource {
    suspend fun upsertTask(task: Task): Result<TaskId, DataError.Local>
    suspend fun getTasks(): List<Task>
    fun upsertEvent(event: Event)
    fun upsertReminder(reminder: Reminder)
}