package ch.protonmail.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import ch.protonmail.domain.di.IoDispatcher
import ch.protonmail.domain.interactors.Resource
import ch.protonmail.domain.interactors.TaskEntity
import ch.protonmail.domain.interactors.TasksRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class GetTaskUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: TasksRepository,
    private val taskMapper: TaskToUIEntityUseCase
) {

    suspend operator fun invoke(id: String): LiveData<Resource<TaskEntity>> {
        return liveData(dispatcher) {
            emitSource(repository.getTask(id))
        }.switchMap { value -> taskMapper(value) }
    }
}