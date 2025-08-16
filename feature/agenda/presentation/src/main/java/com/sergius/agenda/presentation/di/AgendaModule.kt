package com.sergius.agenda.presentation.di

import com.sergius.agenda.presentation.agendaoverview.AgendaViewModel
import com.sergius.agenda.presentation.task.AgendaItemDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val agendaModule = module {
    viewModelOf(::AgendaViewModel)
    viewModelOf(::AgendaItemDetailsViewModel)
}