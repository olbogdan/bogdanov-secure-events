package ch.protonmail.data.remote.retrofit

import ch.protonmail.data.remote.NetworkResult
import ch.protonmail.data.remote.TaskDTO
import retrofit2.http.GET

interface TasksApiService {
//    @GET("/tasks.json")
    @GET("/tasks")
    suspend fun getTasks() : NetworkResult<List<TaskDTO>>
}