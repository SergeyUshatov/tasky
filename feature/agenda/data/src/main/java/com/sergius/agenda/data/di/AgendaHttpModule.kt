package com.sergius.agenda.data.di

import com.sergius.agenda.data.HttpRemoteEventDataSource
import com.sergius.agenda.data.HttpRemoteReminderDataSource
import com.sergius.agenda.data.HttpRemoteTaskDataSource
import com.sergius.core.domain.RemoteEventDataSource
import com.sergius.core.domain.RemoteReminderDataSource
import com.sergius.core.domain.RemoteTaskDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {
    singleOf(::HttpRemoteTaskDataSource).bind<RemoteTaskDataSource>()
    singleOf(::HttpRemoteEventDataSource).bind<RemoteEventDataSource>()
    singleOf(::HttpRemoteReminderDataSource).bind<RemoteReminderDataSource>()
}