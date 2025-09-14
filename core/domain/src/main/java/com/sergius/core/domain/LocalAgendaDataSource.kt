package com.sergius.core.domain

import com.sergius.core.domain.model.AgendaItem
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias ItemId = String
interface LocalAgendaDataSource {
    suspend fun upsertTask(task: Task): Result<ItemId, DataError.Local>
    fun getTasks(): Flow<List<Task>>
    suspend fun deleteTask(itemId: ItemId)

    suspend fun upsertEvent(event: Event): Result<ItemId, DataError.Local>
    fun getEvents(): Flow<List<Event>>
    suspend fun deleteEvent(itemId: ItemId)

    suspend fun upsertReminder(reminder: Reminder): Result<ItemId, DataError.Local>
    fun getReminders(): Flow<List<Reminder>>
    suspend fun deleteReminder(itemId: ItemId)

    fun getAgendaForDate(date: LocalDate): Flow<List<AgendaItem>>
}