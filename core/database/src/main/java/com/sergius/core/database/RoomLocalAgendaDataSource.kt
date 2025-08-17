package com.sergius.core.database

import android.database.sqlite.SQLiteFullException
import com.sergius.core.database.dao.TaskDao
import com.sergius.core.database.mapper.toTaskDto
import com.sergius.core.database.mapper.toTaskEntity
import com.sergius.core.domain.LocalAgendaDataSource
import com.sergius.core.domain.TaskId
import com.sergius.core.domain.dto.EventDto
import com.sergius.core.domain.dto.ReminderDto
import com.sergius.core.domain.dto.TaskDto
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.Result

class RoomLocalAgendaDataSource(
    private val taskDao: TaskDao,
): LocalAgendaDataSource {

    override suspend fun upsertTask(taskDto: TaskDto): Result<TaskId, DataError.Local> {
        return try {
            val entity = taskDto.toTaskEntity()
            taskDao.upsertTask(entity)
            Result.Success(entity.id)
        } catch (_: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getTasks(): List<TaskDto> {
        return taskDao.getTasks().map { it.toTaskDto() }
    }


    override fun upsertEvent(eventDto: EventDto) {
        TODO("Not yet implemented")
    }

    override fun upsertReminder(reminderDto: ReminderDto) {
        TODO("Not yet implemented")
    }
}