package com.sergius.agenda.presentation.mapper

import com.sergius.agenda.presentation.agendaitem.AgendaItemUiData
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.model.Event
import com.sergius.core.domain.model.Reminder
import com.sergius.core.domain.model.Task

fun Task.toAgendaItemUi() = AgendaItemUiData(
    id = this.id,
    title = this.title,
    description = this.description,
    remindAt = this.remindAt,
    updatedAt = this.updatedAt,
    time = this.time,
    isDone = this.isDone,
    from = 0L,
    to = 0L,
    itemType = AgendaItemType.TASK,
)

fun Event.toAgendaItemUi() = AgendaItemUiData(
    id = this.id,
    title = this.title,
    description = this.description,
    remindAt = this.remindAt,
    updatedAt = this.updatedAt,
    from = this.from,
    to = this.to,
    time = 0L,
    itemType = AgendaItemType.EVENT,
)

fun Reminder.toAgendaItemUi() = AgendaItemUiData(
    id = this.id,
    title = this.title,
    description = this.description,
    remindAt = this.remindAt,
    updatedAt = this.updatedAt,
    time = this.time,
    from = 0L,
    to = 0L,
    itemType = AgendaItemType.REMINDER,
)