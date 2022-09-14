package ch.protonmail.android.protonmailtest.presentation.tasks.taskslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.data.APIClient
import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.data.TasksApi
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskFilter
import kotlinx.coroutines.launch

class TasksViewModel(val taskFilter: TaskFilter) : ViewModel() {

    val tasks = MutableLiveData<List<Task>>()

    init {
        requestTasks()
    }

    fun requestTasks() {
        val api = APIClient.getInstance().create(TasksApi::class.java)
        viewModelScope.launch {
            val result = api.getTasks().body()
            if (result != null) {
                Log.d("bogdanov", result.toString())
                result.let {
                    tasks.postValue(it)
                }
            }
        }
    }

    @Suppress("unused")
    class TasksViewModelFactory(private val taskFilter: TaskFilter) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
                return TasksViewModel(taskFilter) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}