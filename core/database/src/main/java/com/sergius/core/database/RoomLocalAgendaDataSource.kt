package com.sergius.core.database

import android.database.sqlite.SQLiteFullException
import com.sergius.core.database.dao.EventDao
import com.sergius.core.database.dao.ReminderDao
import com.sergius.core.database.dao.TaskDao
import com.sergius.core.database.mapper.toAgendaItem
import com.sergius.core.database.mapper.toEvent
import com.sergius.core.database.mapper.toEventEntity
import com.sergius.core.database.mapper.toReminder
import com.sergius.core.database.mapper.toReminderEntity
import com.sergius.core.database.mapper.toTask
import com.sergius.core.database.mapper.toTaskEntity
import com.sergius.core.domain.ItemId
import com.sergius.core.domain.LocalAgendaDataSource
import com.sergius.core.domain.model.AgendaItem
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class RoomLocalAgendaDataSource(
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
    private val reminderDao: ReminderDao,
) : LocalAgendaDataSource {

    override suspend fun upsertTask(task: Task): Result<ItemId, DataError.Local> {
        return try {
            val entity = task.toTaskEntity()
            taskDao.upsertTask(entity)
            Result.Success(entity.id)
        } catch (_: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getTasksForDate(date: LocalDate): Flow<List<Task>> {
        return taskDao.getTasks(
            dateFrom = startOfDay(date),
            dateTo = endOfDay(date)
        ).map { it.map { it.toTask() } }
    }

    override suspend fun deleteTask(itemId: ItemId) {
        taskDao.deleteTask(itemId)
    }

    override suspend fun upsertEvent(event: Event): Result<ItemId, DataError.Local> {
        return try {
            val entity = event.toEventEntity()
            eventDao.upsertEvent(entity)
            Result.Success(entity.id)
        } catch (_: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getEventsForDate(date: LocalDate): Flow<List<Event>> {
        return eventDao.getEvents(
            dateFrom = startOfDay(date),
            dateTo = endOfDay(date)
        ).map { it.map { it.toEvent() } }
    }

    override suspend fun deleteEvent(itemId: ItemId) {
        eventDao.deleteEvent(itemId)
    }

    override suspend fun upsertReminder(reminder: Reminder): Result<ItemId, DataError.Local> {
        return try {
            val entity = reminder.toReminderEntity()
            reminderDao.upsertReminder(entity)
            Result.Success(entity.id)
        } catch (_: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getRemindersForDate(date: LocalDate): Flow<List<Reminder>> {
        val start = startOfDay(date)
        val end = endOfDay(date)
        return reminderDao.getReminders(
            dateFrom = start,
            dateTo = end
        ).map { it.map { it.toReminder() } }
    }

    private fun startOfDay(date: LocalDate) = date
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    private fun endOfDay(date: LocalDate) = date
        .plusDays(1)
        .atStartOfDay(ZoneId.systemDefault())
        .minusSeconds(1)
        .toInstant()
        .toEpochMilli()

    override suspend fun deleteReminder(itemId: ItemId) {
        reminderDao.deleteReminder(itemId)
    }

    override fun getAgendaForDate(date: LocalDate): Flow<List<AgendaItem>> {
        return combine(
            getTasksForDate(date),
            getEventsForDate(date),
            getRemindersForDate(date)
        ) { tasks, events, reminders ->
            (tasks.map<Task, AgendaItem> { it.toAgendaItem() }
                    + events.map<Event, AgendaItem> { it.toAgendaItem() }
                    + reminders.map<Reminder, AgendaItem> { it.toAgendaItem() })
                .sortedBy { it.time }
        }
    }
}