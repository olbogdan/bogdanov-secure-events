package ch.protonmail.android.protonmailtest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class MainViewModel : ViewModel() {

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

    class FetchDataFromServerTask : AsyncTask<String, String, List<Task>>() {
        override fun doInBackground(vararg p0: String?): List<Task> {
            val url = URL("https://proton-android-testcloud.europe-west1.firebasedatabase.app/tasks.json")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            httpURLConnection.inputStream
            val responseCode: Int = httpURLConnection.responseCode

            var response: String = ""
            if (responseCode == 200) {
                response = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
            }
            return Json.decodeFromString(ListSerializer(Task.serializer()), response)
        }
    }
}