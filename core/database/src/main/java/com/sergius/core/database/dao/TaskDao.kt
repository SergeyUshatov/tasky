package com.sergius.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergius.core.database.entity.TaskEntity

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Query("SELECT * FROM TaskEntity ORDER BY time ASC")
    fun getTasks(): List<TaskEntity>

    @Query("DELETE FROM TaskEntity WHERE id = :taskId")
    suspend fun deleteTask(taskId: String)
}