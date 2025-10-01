package com.sergius.agenda.data

import com.sergius.agenda.data.dto.ConfirmUploadRequest
import com.sergius.agenda.data.dto.CreateEventRequest
import com.sergius.agenda.data.dto.EventDto
import com.sergius.agenda.data.dto.EventResponse
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
import com.sergius.core.domain.util.onSuccess
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.time.LocalDate

private const val _EVENT = "/event"

class HttpRemoteEventDataSource(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
) : RemoteEventDataSource {
    override suspend fun createEvent(event: Event): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.post<CreateEventRequest, EventResponse>(
                route = _EVENT,
                body = event.toCreateEventRequest()
            )
        }.await()
            .onSuccess {
                if (it.event.photoKeys.isNotEmpty()) {
                    applicationScope.async {
                        httpClient.post<ConfirmUploadRequest, EventResponse>(
                            route = "$_EVENT/${it.event.id}/confirm-upload",
                            body = ConfirmUploadRequest(uploadedKeys = it.event.photoKeys.map { it.key })
                        )
                    }.await()
                }
            }
            .asEmptyDataResult()
    }

    override fun getEventsForDate(date: LocalDate): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvent(itemId: ItemId) {
        applicationScope.async {
            httpClient.delete<Unit>(
                route = _EVENT,
                queryParameters = mapOf("eventId" to itemId)
            )
        }.await()
    }

    override suspend fun updateEvent(event: Event): EmptyResult<DataError.Network> {
        return applicationScope.async {
            httpClient.put<CreateEventRequest, EventDto>(
                route = _EVENT,
                body = event.toCreateEventRequest()
            )
        }.await().asEmptyDataResult()
    }

    override suspend fun getEvent(itemId: ItemId): Result<Event, DataError.Network> {
        val result = applicationScope.async {
            httpClient.get<EventResponse>(
                route = "$_EVENT/$itemId",
            )
        }.await()

        return when (result) {
            is Result.Success -> Result.Success<Event>(result.data.event.toEvent())
            is Result.Error -> Result.Error<DataError.Network>(result.error)
        }
    }
}