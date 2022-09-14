package ch.protonmail.android.protonmailtest.data

import retrofit2.Response
import retrofit2.http.GET

interface TasksApi {
    @GET("/tasks.json")
    suspend fun getTasks() : Response<List<Task>>
}