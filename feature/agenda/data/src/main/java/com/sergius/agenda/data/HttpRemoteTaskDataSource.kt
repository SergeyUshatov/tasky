package com.sergius.agenda.data

import com.sergius.agenda.data.dto.CreateTaskRequest
import com.sergius.agenda.data.dto.TaskResponse
import com.sergius.agenda.data.mappers.toCreateTaskRequest
import com.sergius.agenda.data.mappers.toTask
import com.sergius.core.data.networking.delete
import com.sergius.core.data.networking.get
import com.sergius.core.data.networking.post
import com.sergius.core.data.networking.put
import com.sergius.core.domain.ItemId
import com.sergius.core.domain.RemoteTaskDataSource
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import com.sergius.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.time.LocalDate

private const val _TASK = "/task"

class HttpRemoteTaskDataSource(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
) : RemoteTaskDataSource {
    override suspend fun createTask(task: Task): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.post<CreateTaskRequest, TaskResponse>(
                route = _TASK,
                body = task.toCreateTaskRequest()
            )
        }.await().asEmptyDataResult()
    }

    override fun getTasksForDate(date: LocalDate): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(itemId: ItemId) {
        applicationScope.async {
            httpClient.delete<Unit>(
                route = "$_TASK/$itemId"
            )
        }.await()
    }

    override suspend fun updateTask(task: Task): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.put<CreateTaskRequest, TaskResponse>(
                route = _TASK,
                body = task.toCreateTaskRequest()
            )
        }.await().asEmptyDataResult()
    }

    override suspend fun getTask(itemId: ItemId): Result<Task, DataError.Network> {
        val result = applicationScope.async {
            httpClient.get<TaskResponse>(
                route = "$_TASK/$itemId",
            )
        }.await()

        return when (result) {
            is Result.Success -> Result.Success<Task>(result.data.toTask())
            is Result.Error -> Result.Error<DataError.Network>(result.error)
        }
    }
}