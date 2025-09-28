package com.sergius.core.domain

import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RemoteAgendaDataSource {
    suspend fun createTask(task: Task): EmptyResult<DataError.Network>
    fun getTasksForDate(date: LocalDate): Flow<List<Task>>
    suspend fun deleteTask(itemId: ItemId)

    suspend fun upsertEvent(event: Event): Result<ItemId, DataError.Local>
    fun getEventsForDate(date: LocalDate): Flow<List<Event>>
    suspend fun deleteEvent(itemId: ItemId)

    suspend fun upsertReminder(reminder: Reminder): Result<ItemId, DataError.Local>
    fun getRemindersForDate(date: LocalDate): Flow<List<Reminder>>
    suspend fun deleteReminder(itemId: ItemId)
}