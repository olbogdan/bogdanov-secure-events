package ch.protonmail.android.protonmailtest.data.local

import android.content.Context
import androidx.room.*
import java.util.*

private const val DB_NAME = "tasks_database"

@Database(entities = [(Task::class)], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object {
        fun create(context: Context): TasksDatabase {
            return Room.databaseBuilder(
                context,
                TasksDatabase::class.java,
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