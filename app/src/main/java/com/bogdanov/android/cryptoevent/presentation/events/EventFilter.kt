package com.bogdanov.android.cryptoevent.presentation.events

enum class EventFilter(val value: Int) {
    ALL_EVENTS(0),
    UPCOMING_EVENTS(1);

    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}