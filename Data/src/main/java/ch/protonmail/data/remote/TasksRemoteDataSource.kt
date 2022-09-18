package ch.protonmail.data.remote

import ch.protonmail.data.remote.retrofit.TasksApiService

//todo: reduce sever calls by ETags, Conditional Caching, joining to pull or running requests
class TasksRemoteDataSource(private val api: TasksApiService) {
    suspend fun getTasks(): NetworkResult<List<TaskDTO>> = api.getTasks()
}