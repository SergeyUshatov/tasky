package com.sergius.core.domain.model

sealed interface PickerType {
    data object PickerFrom: PickerType
    data object PickerTo: PickerType
    data object PickerAt: PickerType
}