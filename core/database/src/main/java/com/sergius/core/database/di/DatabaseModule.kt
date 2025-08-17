package com.sergius.core.database.di

import androidx.room.Room
import com.sergius.core.database.RoomLocalAgendaDataSource
import com.sergius.core.database.TaskyDatabase
import com.sergius.core.domain.LocalAgendaDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            TaskyDatabase::class.java,
            "Tasky.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single { get<TaskyDatabase>().taskDao }
    single { get<TaskyDatabase>().eventDao }
    single { get<TaskyDatabase>().reminderDao }

    singleOf(::RoomLocalAgendaDataSource).bind<LocalAgendaDataSource>()
}