package com.sergius.core.domain

import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result

typealias ItemId = String
interface LocalAgendaDataSource {
    suspend fun upsertTask(task: Task): Result<ItemId, DataError.Local>
    suspend fun getTasks(): List<Task>
    suspend fun deleteTask(itemId: ItemId)

    suspend fun upsertEvent(event: Event): Result<ItemId, DataError.Local>
    suspend fun getEvents(): List<Event>
    suspend fun deleteEvent(itemId: ItemId)

    suspend fun upsertReminder(reminder: Reminder): Result<ItemId, DataError.Local>
    suspend fun getReminders(): List<Reminder>
    suspend fun deleteReminder(itemId: ItemId)
}