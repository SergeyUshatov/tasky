package com.sergius.agenda.data.di

import com.sergius.agenda.data.HttpRemoteAgendaDataSource
import com.sergius.core.domain.RemoteAgendaDataSource
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {
    single {
        HttpRemoteAgendaDataSource(get(), get())
    }.bind<RemoteAgendaDataSource>()
}