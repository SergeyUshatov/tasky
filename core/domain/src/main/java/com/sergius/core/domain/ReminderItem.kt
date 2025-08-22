package com.sergius.core.domain

private const val sec = 1000L
private const val minute = 60 * sec
private const val hour = 60 * minute
private const val day = 24 * hour

enum class ReminderItem(
    val text: String,
    val longVal: Long
) {
    MINUTES_10("10 minutes before", 10 * minute),
    MINUTES_30("30 minutes before", 30 * minute),
    HOUR_1("1 hour before", hour),
    HOUR_6("6 hours before", 6 * hour),
    DAY_1("1 day before", day)
}