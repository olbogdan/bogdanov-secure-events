package ch.protonmail.android.protonmailtest.presentation.taskslist

import androidx.lifecycle.*
import ch.protonmail.android.protonmailtest.data.Task
import ch.protonmail.android.protonmailtest.di.IoDispatcher
import ch.protonmail.android.protonmailtest.interactors.GetTasksUseCase
import ch.protonmail.android.protonmailtest.presentation.TaskFilter
import ch.protonmail.android.protonmailtest.presentation.UIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class TasksListViewModel
@AssistedInject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @Assisted private val filter: TaskFilter,
    private val useCase: GetTasksUseCase
) : ViewModel() {

    val uiState: LiveData<UIState<List<Task>>> get() = _uiState
    private val _uiState = MutableLiveData<UIState<List<Task>>>()

    init {
        requestTasks()
    }

    private fun requestTasks() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(dispatcher) {
            useCase.getTasks()
                .onSuccess { _uiState.postValue(UIState.Success(it)) }
                .onFailure { _uiState.postValue(UIState.Failure(it.toString())) }
        }
    }

    class Factory(private val assistedFactory: VMFactory, private val filter: TaskFilter) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(filter) as T
        }
    }

    @AssistedFactory
    interface VMFactory {
        fun create(filter: TaskFilter): TasksListViewModel
    }
}