package com.sergius.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sergius.core.database.dao.EventDao
import com.sergius.core.database.dao.ReminderDao
import com.sergius.core.database.dao.TaskDao
import com.sergius.core.database.entity.EventEntity
import com.sergius.core.database.entity.ReminderEntity
import com.sergius.core.database.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        EventEntity::class,
        ReminderEntity::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class TaskyDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val eventDao: EventDao
    abstract val reminderDao: ReminderDao
}