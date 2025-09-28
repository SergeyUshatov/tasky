package com.sergius.core.domain

import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RemoteTaskDataSource {
    suspend fun createTask(task: Task): EmptyResult<DataError.Network>
    suspend fun updateTask(task: Task): EmptyResult<DataError.Network>
    suspend fun getTask(itemId: ItemId): Result<Task, DataError.Network>
    fun getTasksForDate(date: LocalDate): Flow<List<Task>>
    suspend fun deleteTask(itemId: ItemId)
}