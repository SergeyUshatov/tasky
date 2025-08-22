package com.sergius.core.database

import android.database.sqlite.SQLiteFullException
import com.sergius.core.database.dao.TaskDao
import com.sergius.core.database.mapper.toTask
import com.sergius.core.database.mapper.toTaskEntity
import com.sergius.core.domain.LocalAgendaDataSource
import com.sergius.core.domain.TaskId
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result

class RoomLocalAgendaDataSource(
    private val taskDao: TaskDao,
): LocalAgendaDataSource {

    override suspend fun upsertTask(task: Task): Result<TaskId, DataError.Local> {
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


    override fun upsertEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override fun upsertReminder(reminder: Reminder) {
        TODO("Not yet implemented")
    }
}