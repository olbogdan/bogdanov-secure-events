package ch.protonmail.android.protonmailtest.interactors.usecases

import androidx.lifecycle.*
import ch.protonmail.android.protonmailtest.di.IoDispatcher
import ch.protonmail.android.protonmailtest.interactors.Resource
import ch.protonmail.android.protonmailtest.interactors.TasksRepository
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskUIEntity
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject


class GetTasksUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: TasksRepository,
    private val tasksMapper: TasksListToUIEntityUseCase
) {

    suspend operator fun invoke(filter: Filter): LiveData<Resource<List<TaskUIEntity>>> {
        return liveData(dispatcher) {
            val resource = when (filter) {
                is Filter.ALL -> repository.getAllTasks()
                is Filter.UPCOMING -> repository.getUpcomingTasks(Date())
            }.distinctUntilChanged()
            emitSource(resource)
        }.switchMap { value ->
            liveData(dispatcher) {
                emitSource(tasksMapper(value))
            }
        }
    }

    sealed class Filter {
        object ALL : Filter()
        object UPCOMING : Filter()
    }
}