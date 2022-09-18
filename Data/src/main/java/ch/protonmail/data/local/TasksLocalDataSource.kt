package ch.protonmail.data.local

import androidx.lifecycle.LiveData
import java.util.Date

class TasksLocalDataSource constructor(private val tasksDao: TasksDao) {

    fun getAllTasks(): LiveData<List<Task>> = tasksDao.getAllTasks()

    fun getUpcomingTasks(targetDate: Date): LiveData<List<Task>> =
        tasksDao.getUpcomingTasks(targetDate)

    fun getTask(id: String): LiveData<Task> = tasksDao.getTask(id)

    suspend fun getTaskImmediate(id: String): Task? = tasksDao.getTaskImmediate(id)

    suspend fun updateTasks(tasks: List<Task>) {
        tasksDao.insertTasks(tasks)
    }

    suspend fun updateTask(task: Task) {
        tasksDao.insertTask(task)
    }
}