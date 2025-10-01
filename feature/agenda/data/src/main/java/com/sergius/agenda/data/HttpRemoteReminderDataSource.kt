package com.sergius.agenda.data

import com.sergius.agenda.data.dto.CreateReminderRequest
import com.sergius.agenda.data.dto.ReminderResponse
import com.sergius.agenda.data.mappers.toCreateReminderRequest
import com.sergius.agenda.data.mappers.toReminder
import com.sergius.core.data.networking.delete
import com.sergius.core.data.networking.get
import com.sergius.core.data.networking.post
import com.sergius.core.data.networking.put
import com.sergius.core.domain.ItemId
import com.sergius.core.domain.RemoteReminderDataSource
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import com.sergius.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.time.LocalDate

private const val _REMINDER = "/reminder"

class HttpRemoteReminderDataSource(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
) : RemoteReminderDataSource {
    override suspend fun createReminder(reminder: Reminder): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.post<CreateReminderRequest, ReminderResponse>(
                route = _REMINDER,
                body = reminder.toCreateReminderRequest()
            )
        }.await().asEmptyDataResult()
    }

    override fun getRemindersForDate(date: LocalDate): List<Reminder> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(itemId: ItemId) {
        applicationScope.async {
            httpClient.delete<Unit>(
                route = "$_REMINDER/$itemId"
            )
        }.await()
    }

    override suspend fun updateReminder(reminder: Reminder): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.put<CreateReminderRequest, ReminderResponse>(
                route = _REMINDER,
                body = reminder.toCreateReminderRequest()
            )
        }.await().asEmptyDataResult()
    }

    override suspend fun getReminder(itemId: ItemId): Result<Reminder, DataError.Network> {
        val result = applicationScope.async {
            httpClient.get<ReminderResponse>(
                route = "$_REMINDER/$itemId",
            )
        }.await()

        return when (result) {
            is Result.Success -> Result.Success<Reminder>(result.data.toReminder())
            is Result.Error -> Result.Error<DataError.Network>(result.error)
        }
    }
}