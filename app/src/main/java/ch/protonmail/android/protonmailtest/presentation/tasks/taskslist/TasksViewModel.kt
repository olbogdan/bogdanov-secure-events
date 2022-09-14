package ch.protonmail.android.protonmailtest.presentation.tasks.taskslist

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskFilter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class TasksViewModel(val taskFilter: TaskFilter) : ViewModel() {

    val tasks = MutableLiveData<List<Task>>()

    fun fetchTasks(activity: AppCompatActivity) {
        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                val list = FetchDataFromServerTask().execute().get()
                tasks.postValue(list)
            }
        })
    }

    fun itemSelected(task: Task) {
        //todo: navigate to details
    }

    class FetchDataFromServerTask : AsyncTask<String, String, List<Task>>() {
        override fun doInBackground(vararg p0: String?): List<Task> {
            val url =
                URL("https://proton-android-testcloud.europe-west1.firebasedatabase.app/tasks.json")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            httpURLConnection.inputStream
            val responseCode: Int = httpURLConnection.responseCode

            var response = ""
            if (responseCode == 200) {
                response = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
            }
            return Json.decodeFromString(ListSerializer(Task.serializer()), response)
        }
    }

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