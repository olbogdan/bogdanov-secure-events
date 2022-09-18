package ch.protonmail.android.protonmailtest.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ch.proton.crypto.Crypto
import ch.protonmail.android.protonmailtest.data.local.Task
import ch.protonmail.android.protonmailtest.di.IoDispatcher
import ch.protonmail.android.protonmailtest.interactors.Resource
import ch.protonmail.android.protonmailtest.interactors.extensions.mapToUIEntity
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskUIEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TaskToUIEntityUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val crypto: Crypto
) {

    operator fun invoke(value: Resource<Task>): LiveData<Resource<TaskUIEntity>> =
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