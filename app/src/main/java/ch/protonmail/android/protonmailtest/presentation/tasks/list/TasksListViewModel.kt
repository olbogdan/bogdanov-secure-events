package ch.protonmail.android.protonmailtest.presentation.tasks.list

import androidx.lifecycle.*
import ch.protonmail.domain.di.IoDispatcher
import ch.protonmail.domain.interactors.Resource
import ch.protonmail.domain.interactors.usecases.GetTasksUseCase
import ch.protonmail.android.protonmailtest.presentation.UIState
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskFilter
import ch.protonmail.domain.interactors.TaskEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher

class TasksListViewModel
@AssistedInject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @Assisted private val filter: TaskFilter,
    private val useCase: GetTasksUseCase
) : ViewModel() {

    val uiState: LiveData<UIState<List<TaskEntity>>> = liveData(dispatcher) {
        emitSource(
            useCase(filter.mapToFilter()).map { result ->
                when (result) {
                    is Resource.Loading -> UIState.Loading
                    is Resource.Success -> UIState.Success(result.data)
                    is Resource.Failure -> UIState.Failure(result.errorMessage)
                }
            }
        )
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

    private fun TaskFilter.mapToFilter(): GetTasksUseCase.Filter {
        return when (this) {
            TaskFilter.ALL_TASKS -> GetTasksUseCase.Filter.ALL
            TaskFilter.UPCOMING_TASKS -> GetTasksUseCase.Filter.UPCOMING
        }
    }
}