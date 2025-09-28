package com.sergius.agenda.data

import com.sergius.agenda.data.mappers.toCreateEventRequest
import com.sergius.agenda.data.mappers.toEvent
import com.sergius.core.data.networking.delete
import com.sergius.core.data.networking.get
import com.sergius.core.data.networking.post
import com.sergius.core.data.networking.put
import com.sergius.core.domain.ItemId
import com.sergius.core.domain.RemoteEventDataSource
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import com.sergius.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class HttpRemoteEventDataSource(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
) : RemoteEventDataSource {
    override suspend fun createEvent(event: Event): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.post<CreateEventRequest, EventResponse>(
                route = "/event",
                body = event.toCreateEventRequest()
            )
        }.await().asEmptyDataResult()
    }

    override fun getEventsForDate(date: LocalDate): Flow<List<Event>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvent(itemId: ItemId) {
        applicationScope.async {
            httpClient.delete<Unit>(
                route = "/event/$itemId"
            )
        }.await()
    }

    override suspend fun updateEvent(event: Event): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.put<CreateEventRequest, EventResponse>(
                route = "/event",
                body = event.toCreateEventRequest()
            )
        }.await().asEmptyDataResult()
    }

    override suspend fun getEvent(itemId: ItemId): Result<Event, DataError.Network> {
        val result = applicationScope.async {
            httpClient.get<EventResponse>(
                route = "/event/$itemId",
            )
        }.await()

        return when (result) {
            is Result.Success -> Result.Success<Event>(result.data.toEvent())
            is Result.Error -> Result.Error<DataError.Network>(result.error)
        }
    }
}