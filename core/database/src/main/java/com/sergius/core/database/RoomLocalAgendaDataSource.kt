package com.sergius.core.database

import android.database.sqlite.SQLiteFullException
import com.sergius.core.database.dao.EventDao
import com.sergius.core.database.dao.ReminderDao
import com.sergius.core.database.dao.TaskDao
import com.sergius.core.database.mapper.toEvent
import com.sergius.core.database.mapper.toEventEntity
import com.sergius.core.database.mapper.toReminder
import com.sergius.core.database.mapper.toReminderEntity
import com.sergius.core.database.mapper.toTask
import com.sergius.core.database.mapper.toTaskEntity
import com.sergius.core.domain.LocalAgendaDataSource
import com.sergius.core.domain.ItemId
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result

class RoomLocalAgendaDataSource(
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
    private val reminderDao: ReminderDao,
): LocalAgendaDataSource {

    override suspend fun upsertTask(task: Task): Result<ItemId, DataError.Local> {
        return try {
            val entity = task.toTaskEntity()
            taskDao.upsertTask(entity)
            Result.Success(entity.id)
        } catch (_: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getTasks(): List<Task> {
        return taskDao.getTasks().map { it.toTask() }
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

    override suspend fun getEvents(): List<Event> {
        return eventDao.getEvents().map { it.toEvent() }
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

    override suspend fun getReminders(): List<Reminder> {
        return reminderDao.getReminders().map { it.toReminder() }
    }

    override suspend fun deleteReminder(itemId: ItemId) {
        reminderDao.deleteReminder(itemId)
    }
}