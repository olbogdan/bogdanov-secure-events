package ch.protonmail.android.protonmailtest.presentation.tasks.details

import android.util.Log
import androidx.lifecycle.*
import ch.protonmail.android.protonmailtest.presentation.UIState
import ch.protonmail.domain.di.IoDispatcher
import ch.protonmail.domain.interactors.Resource
import ch.protonmail.domain.interactors.TaskEntity
import ch.protonmail.domain.interactors.usecases.DownloadImageUseCase
import ch.protonmail.domain.interactors.usecases.GetTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVE_ARG_TASK_KEY = "task"
private const val TAG = "bogdanov"

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getTasksUseCase: GetTaskUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    val uiState: LiveData<UIState<TaskEntity>> = liveData(dispatcher) {
        val taskFromState = getTaskFromState()
        emitSource(
            getTasksUseCase(taskFromState.id).map { resource ->
                when (resource) {
                    is Resource.Loading -> UIState.Loading
                    is Resource.Success -> UIState.Success(resource.data)
                    is Resource.Failure -> UIState.Failure(resource.errorMessage)
                }
            }
        )
    }

    fun downloadImageForTask(taskId: String) {
        viewModelScope.launch(dispatcher) {
            when (downloadImageUseCase(taskId)) {
                is Resource.Success -> Log.d(TAG, "downloadImageForTask started")
                is Resource.Failure -> Log.d(TAG, "downloadImageForTask failed")
                is Resource.Loading -> Log.d(TAG, "downloadImageForTask loading")
            }
        }
    }

    private fun getTaskFromState(): TaskEntity {
        return state.get<TaskEntity>(SAVE_ARG_TASK_KEY)
            ?: throw IllegalStateException("SavedStateHandle expected to have $SAVE_ARG_TASK_KEY argument")
    }
}