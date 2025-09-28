package com.sergius.core.domain

import com.sergius.core.domain.model.Event
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RemoteEventDataSource {
    suspend fun createEvent(event: Event): EmptyResult<DataError.Network>
    suspend fun updateEvent(event: Event): EmptyResult<DataError.Network>
    suspend fun getEvent(itemId: ItemId): Result<Event, DataError.Network>
    fun getEventsForDate(date: LocalDate): Flow<List<Event>>
    suspend fun deleteEvent(itemId: ItemId)
}