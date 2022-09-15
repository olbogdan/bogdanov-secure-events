package ch.protonmail.android.protonmailtest.interactors

import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.data.remote.NetworkResult
import ch.protonmail.android.protonmailtest.data.remote.retrofit.TasksApiService
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val api: TasksApiService) {

    suspend fun getTasks(): Result<List<Task>> {
        return when (val result = api.getTasks()) {
            is NetworkResult.Success -> Result.success(result.data)
            is NetworkResult.Error -> Result.failure(Exception("${result.code}"))
            is NetworkResult.Exception -> Result.failure(result.e)
        }
    }
}