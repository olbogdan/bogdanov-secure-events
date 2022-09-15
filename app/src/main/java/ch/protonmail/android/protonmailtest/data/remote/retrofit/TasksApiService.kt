package ch.protonmail.android.protonmailtest.data.remote.retrofit

import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.data.remote.NetworkResult
import retrofit2.http.GET

interface TasksApiService {
//    @GET("/tasks.json")
    @GET("/tasks")
    suspend fun getTasks() : NetworkResult<List<Task>>
}