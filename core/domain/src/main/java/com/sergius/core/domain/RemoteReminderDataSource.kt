package com.sergius.core.domain

import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RemoteReminderDataSource {
    suspend fun createReminder(reminder: Reminder): EmptyResult<DataError.Network>
    suspend fun updateReminder(reminder: Reminder): EmptyResult<DataError.Network>
    suspend fun getReminder(itemId: ItemId): Result<Reminder, DataError.Network>
    fun getRemindersForDate(date: LocalDate): Flow<List<Reminder>>
    suspend fun deleteReminder(itemId: ItemId)
}