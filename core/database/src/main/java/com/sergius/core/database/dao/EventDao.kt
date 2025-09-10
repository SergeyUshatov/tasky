package com.sergius.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergius.core.database.entity.EventEntity

@Dao
interface EventDao {

    @Upsert
    fun upsertEvent(entity: EventEntity)

    @Query("SELECT * FROM EventEntity ORDER BY `from` ASC")
    fun getEvents(): List<EventEntity>

    @Query("DELETE FROM EventEntity WHERE id = :eventId")
    suspend fun deleteEvent(eventId: String)
}