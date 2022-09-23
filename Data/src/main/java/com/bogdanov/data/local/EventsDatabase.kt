package com.bogdanov.data.local

import android.content.Context
import androidx.room.*
import java.util.*

private const val DB_NAME = "events_database"

@Database(entities = [(Event::class)], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class EventsDatabase : RoomDatabase() {

    abstract fun eventsDao(): EventDao

    companion object {
        fun create(context: Context): EventsDatabase {
            return Room.databaseBuilder(
                context,
                EventsDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}

class Converter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toDate(value: Long): Date = Date(value)

        @TypeConverter
        @JvmStatic
        fun fromDate(date: Date): Long = date.time
    }
}