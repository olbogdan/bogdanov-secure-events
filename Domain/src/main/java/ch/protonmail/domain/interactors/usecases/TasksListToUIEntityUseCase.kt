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

class TasksListToUIEntityUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val crypto: Crypto
) {

    operator fun invoke(resource: Resource<List<Task>>): LiveData<Resource<List<TaskEntity>>> =
        liveData(dispatcher) {
            when (resource) {
                is Resource.Success -> {
                    // todo: as an improvement we can decode and consume entities one by one, so that user will see them dynamically (don't need to wait all decoded list)
                    val uiEntities = resource.data.map { it.mapToUIEntity(crypto) }
                    emit(Resource.Success(uiEntities))
                }
                is Resource.Loading -> emit(resource)
                is Resource.Failure -> emit(resource)
            }
        }
}