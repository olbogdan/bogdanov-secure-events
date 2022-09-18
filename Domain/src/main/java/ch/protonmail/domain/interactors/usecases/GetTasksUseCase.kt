package ch.protonmail.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import ch.protonmail.domain.di.IoDispatcher
import ch.protonmail.domain.interactors.Resource
import ch.protonmail.domain.interactors.TaskEntity
import ch.protonmail.domain.interactors.TasksRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject


class GetTasksUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: TasksRepository,
    private val tasksMapper: TasksListToUIEntityUseCase
) {

    operator fun invoke(filter: Filter): LiveData<Resource<List<TaskEntity>>> {
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