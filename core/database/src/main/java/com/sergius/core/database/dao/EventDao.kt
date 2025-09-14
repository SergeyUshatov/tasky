package com.sergius.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergius.core.database.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Upsert
    suspend fun upsertEvent(entity: EventEntity)

    @Query("SELECT * FROM EventEntity WHERE `from` BETWEEN :dateFrom AND :dateTo ORDER BY `from` ASC")
    fun getEvents(dateFrom: Long, dateTo: Long): Flow<List<EventEntity>>

    @Query("DELETE FROM EventEntity WHERE id = :eventId")
    suspend fun deleteEvent(eventId: String)
}