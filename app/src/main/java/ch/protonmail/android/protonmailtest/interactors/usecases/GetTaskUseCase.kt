package ch.protonmail.android.protonmailtest.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import ch.protonmail.android.protonmailtest.di.IoDispatcher
import ch.protonmail.android.protonmailtest.interactors.Resource
import ch.protonmail.android.protonmailtest.interactors.TasksRepository
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskUIEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class GetTaskUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: TasksRepository,
    private val taskMapper: TaskToUIEntityUseCase
) {

    suspend operator fun invoke(id: String): LiveData<Resource<TaskUIEntity>> {
        return liveData(dispatcher) {
            emitSource(repository.getTask(id))
        }.switchMap { value -> taskMapper(value) }
    }
}