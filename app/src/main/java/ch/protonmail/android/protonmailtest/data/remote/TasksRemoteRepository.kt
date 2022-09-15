package ch.protonmail.android.protonmailtest.data.remote

import ch.protonmail.android.protonmailtest.data.remote.retrofit.TasksApiService

class TasksRemoteRepository(private val api: TasksApiService) {
    suspend fun getTasks() = api.getTasks()
}