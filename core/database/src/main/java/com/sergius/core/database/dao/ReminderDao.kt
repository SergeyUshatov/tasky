package com.sergius.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergius.core.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Upsert
    suspend fun upsertReminder(reminder: ReminderEntity)

    @Query("SELECT * FROM ReminderEntity WHERE time BETWEEN :dateFrom AND :dateTo ORDER BY time ASC")
    fun getReminders(dateFrom: Long, dateTo: Long): Flow<List<ReminderEntity>>

    @Query("DELETE FROM ReminderEntity WHERE id = :reminderId")
    suspend fun deleteReminder(reminderId: String)
}