package com.bogdanov.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface EventDao {

    @Query("SELECT * FROM Event ORDER BY creationDate")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM Event WHERE dueDate > :targetDate ORDER BY dueDate")
    fun getUpcomingEvents(targetDate: Date): LiveData<List<Event>>

    @Query("SELECT * FROM Event WHERE id == :id")
    fun getEvent(id: String): LiveData<Event>

    @Query("SELECT * FROM Event WHERE id == :id")
    suspend fun getEventImmediate(id: String): Event?

    //todo: implement custom conflict resolving strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: List<Event>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)
}