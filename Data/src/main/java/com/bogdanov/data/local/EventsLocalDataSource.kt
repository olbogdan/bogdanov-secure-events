package com.bogdanov.data.local

import androidx.lifecycle.LiveData
import java.util.Date

class EventsLocalDataSource constructor(private val eventDao: EventDao) {

    fun getAllEvents(): LiveData<List<Event>> = eventDao.getAllEvents()

    fun getUpcomingEvents(targetDate: Date): LiveData<List<Event>> =
        eventDao.getUpcomingEvents(targetDate)

    fun getEvent(id: String): LiveData<Event> = eventDao.getEvent(id)

    suspend fun getEventImmediate(id: String): Event? = eventDao.getEventImmediate(id)

    suspend fun updateEvents(events: List<Event>) {
        eventDao.insertEvents(events)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.insertEvent(event)
    }
}