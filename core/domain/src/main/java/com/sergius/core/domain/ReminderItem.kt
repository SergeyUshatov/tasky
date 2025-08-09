package com.sergius.core.domain

enum class ReminderItem(val text: String) {
    MINUTES_10("10 minutes before"),
    MINUTES_30("30 minutes before"),
    HOUR_1("1 hour before"),
    HOUR_6("6 hours before"),
    DAY_1("1 day before")
}