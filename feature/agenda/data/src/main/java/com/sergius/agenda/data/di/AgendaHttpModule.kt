package com.sergius.agenda.data.di

import com.sergius.agenda.data.HttpRemoteEventDataSource
import com.sergius.agenda.data.HttpRemoteReminderDataSource
import com.sergius.agenda.data.HttpRemoteTaskDataSource
import com.sergius.core.domain.RemoteEventDataSource
import com.sergius.core.domain.RemoteReminderDataSource
import com.sergius.core.domain.RemoteTaskDataSource
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {
    single {
        HttpRemoteTaskDataSource(get(), get())
    }.bind<RemoteTaskDataSource>()

    single {
        HttpRemoteEventDataSource(get(), get())
    }.bind<RemoteEventDataSource>()

    single {
        HttpRemoteReminderDataSource(get(), get())
    }.bind<RemoteReminderDataSource>()
}