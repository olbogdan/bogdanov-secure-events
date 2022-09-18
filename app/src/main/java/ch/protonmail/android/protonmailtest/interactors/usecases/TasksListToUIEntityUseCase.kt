package ch.protonmail.android.protonmailtest.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ch.protonmail.android.protonmailtest.crypto.Crypto
import ch.protonmail.android.protonmailtest.data.local.Task
import ch.protonmail.android.protonmailtest.di.IoDispatcher
import ch.protonmail.android.protonmailtest.interactors.Resource
import ch.protonmail.android.protonmailtest.interactors.extensions.mapToUIEntity
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskUIEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TasksListToUIEntityUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val crypto: Crypto
) {

    operator fun invoke(value: Resource<List<Task>>): LiveData<Resource<List<TaskUIEntity>>> =
        liveData(dispatcher) {
            when (value) {
                is Resource.Success -> {
                    // todo: as an improvement we can decode and consume entities one by one, so that user will see them dynamically (don't need to wait all decoded list)
                    val uiEntities = value.data.map { it.mapToUIEntity(crypto) }
                    emit(Resource.Success(uiEntities))
                }
                is Resource.Loading -> emit(value)
                is Resource.Failure -> emit(value)
            }
        }
}