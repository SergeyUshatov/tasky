package com.sergius.agenda.data

import com.sergius.agenda.data.mappers.toCreateTaskRequest
import com.sergius.core.data.networking.delete
import com.sergius.core.data.networking.post
import com.sergius.core.domain.ItemId
import com.sergius.core.domain.RemoteAgendaDataSource
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import com.sergius.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class HttpRemoteAgendaDataSource(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
) : RemoteAgendaDataSource {
    override suspend fun createTask(task: Task): EmptyResult<DataError.Network> {
        val body = task.toCreateTaskRequest()

        return applicationScope.async {
            httpClient.post<CreateTaskRequest, CreateTaskResponse>(
                route = "/task",
                body = body
            )
        }.await().asEmptyDataResult()
    }

    override fun getTasksForDate(date: LocalDate): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(itemId: ItemId) {
        applicationScope.async {
            httpClient.delete<Unit>(
                route = "/task/$itemId"
            )
        }.await()
    }

    override suspend fun upsertEvent(event: Event): Result<ItemId, DataError.Local> {
        TODO("Not yet implemented")
    }

    override fun getEventsForDate(date: LocalDate): Flow<List<Event>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvent(itemId: ItemId) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertReminder(reminder: Reminder): Result<ItemId, DataError.Local> {
        TODO("Not yet implemented")
    }

    override fun getRemindersForDate(date: LocalDate): Flow<List<Reminder>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(itemId: ItemId) {
        TODO("Not yet implemented")
    }
}