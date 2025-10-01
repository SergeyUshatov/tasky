package com.sergius.core.domain

import com.sergius.core.domain.model.AgendaItem
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias ItemId = String
interface LocalAgendaDataSource {
    suspend fun upsertTask(task: Task): Result<ItemId, DataError.Local>
    fun getTasksForDate(date: LocalDate): Flow<List<Task>>
    suspend fun deleteTask(itemId: ItemId): EmptyResult<DataError.Local>

    suspend fun upsertEvent(event: Event): Result<ItemId, DataError.Local>
    fun getEventsForDate(date: LocalDate): Flow<List<Event>>
    suspend fun deleteEvent(itemId: ItemId): EmptyResult<DataError.Local>

    suspend fun upsertReminder(reminder: Reminder): Result<ItemId, DataError.Local>
    fun getRemindersForDate(date: LocalDate): Flow<List<Reminder>>
    suspend fun deleteReminder(itemId: ItemId): EmptyResult<DataError.Local>

    fun getAgendaForDate(date: LocalDate): Flow<List<AgendaItem>>
}