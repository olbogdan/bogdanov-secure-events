package ch.protonmail.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface TasksDao {

    @Query("SELECT * FROM Task ORDER BY creationDate")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE dueDate > :targetDate ORDER BY dueDate")
    fun getUpcomingTasks(targetDate: Date): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id == :id")
    fun getTask(id: String): LiveData<Task>

    @Query("SELECT * FROM Task WHERE id == :id")
    suspend fun getTaskImmediate(id: String): Task?

    //todo: implement custom conflict resolving strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(tasks: List<Task>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(tasks: Task)
}