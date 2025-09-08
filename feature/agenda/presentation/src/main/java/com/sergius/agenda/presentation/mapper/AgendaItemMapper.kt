package com.sergius.agenda.presentation.mapper

import com.sergius.agenda.presentation.agendaitem.AgendaItemUiData
import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.model.Task

fun Task.toAgendaItemUi() = AgendaItemUiData(
    id = this.id,
    title = this.title,
    description = this.description,
    remindAt = this.remindAt,
    updatedAt = this.updatedAt,
    time = this.time,
    isDone = this.isDone,
    itemType = AgendaItemType.TASK,
)