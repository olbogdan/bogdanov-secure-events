package ch.protonmail.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ch.proton.crypto.Crypto
import ch.protonmail.data.local.Task
import ch.protonmail.domain.di.IoDispatcher
import ch.protonmail.domain.interactors.Resource
import ch.protonmail.domain.interactors.TaskEntity
import ch.protonmail.domain.interactors.extensions.mapToUIEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TaskToUIEntityUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val crypto: Crypto
) {

    operator fun invoke(value: Resource<Task>): LiveData<Resource<TaskEntity>> =
        liveData(dispatcher) {
            when (value) {
                is Resource.Success -> {
                    emit(Resource.Success(value.data.mapToUIEntity(crypto)))
                }
                is Resource.Loading -> emit(value)
                is Resource.Failure -> emit(value)
            }
        }
}