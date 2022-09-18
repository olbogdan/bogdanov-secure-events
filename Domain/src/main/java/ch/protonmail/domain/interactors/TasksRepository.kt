package ch.protonmail.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import ch.protonmail.data.local.Task
import ch.protonmail.data.local.TasksLocalDataSource
import ch.protonmail.data.remote.NetworkResult
import ch.protonmail.data.remote.TaskDTO
import ch.protonmail.data.remote.TasksRemoteDataSource
import ch.protonmail.domain.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

//todo: move it to separate module for better encapsulation
/**
 * Repository works as mediator between Remote and Local data sources.
 * Local datasource consider to be a singe source of true, LiveData notified with dynamic changes.
 * The Remote data is never directly transferred to the user, it is stored into localDataSource.
 * The Remote (Network) fails may be delivered to the user via Resource.Failure().
 */
class TasksRepository @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val remoteDataSource: TasksRemoteDataSource,
    private val localDataSource: TasksLocalDataSource
) {

    fun getAllTasks(): LiveData<Resource<List<Task>>> = performGetOperation(
        databaseQuery = { localDataSource.getAllTasks() },
        remoteCall = { remoteDataSource.getTasks() },
        saveCallResult = { localDataSource.updateTasks(it.map { dto -> dto.toTask() }) }
    )

    fun getUpcomingTasks(targetDate: Date): LiveData<Resource<List<Task>>> = performGetOperation(
        databaseQuery = { localDataSource.getUpcomingTasks(targetDate) },
        remoteCall = { remoteDataSource.getTasks() },
        saveCallResult = { localDataSource.updateTasks(it.map { dto -> dto.toTask() }) }
    )

    fun getTask(id: String): LiveData<Resource<Task>> = liveData(dispatcher) {
        emit(Resource.Loading)
        val resource: LiveData<Resource<Task>> = localDataSource.getTask(id)
            .map { Resource.Success(it) }
        emitSource(resource)
    }

    suspend fun getTaskImmediate(id: String): Task? = localDataSource.getTaskImmediate(id)

    suspend fun updateTask(task: Task) {
        localDataSource.updateTask(task)
    }

    /**
     * High order function controls LiveData flow.
     * The databaseQuery of localDataSource is a single source of true.
     */
    private fun <T, A> performGetOperation(
        databaseQuery: () -> LiveData<T>,
        remoteCall: suspend () -> NetworkResult<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<Resource<T>> = liveData(dispatcher) {

        emit(Resource.Loading)
        val resource: LiveData<Resource<T>> = databaseQuery.invoke().map { Resource.Success(it) }
        emitSource(resource)

        performNetworkFetch(remoteCall, saveCallResult)

        emit(Resource.Loading)
        emitSource(resource)
    }

    /**
     * High order extension fetches data from network and dispatches it to saveCallResult
     */
    private suspend fun <A, T> LiveDataScope<Resource<T>>.performNetworkFetch(
        remoteCall: suspend () -> NetworkResult<A>,
        saveCallResult: suspend (A) -> Unit
    ) {
        when (val networkResult = remoteCall.invoke()) {
            is NetworkResult.Success -> {
                saveCallResult(networkResult.data)
            }
            is NetworkResult.Exception -> {
                emit(Resource.Failure(networkResult.e.toString()))
            }
            is NetworkResult.Error -> {
                emit(Resource.Failure("${networkResult.code} ${networkResult.message}"))
            }
        }
    }
}

private fun TaskDTO.toTask(): Task = Task(
    id = id,
    creationDate = creationDate,
    dueDate = dueDate,
    encryptedDescription = encryptedDescription,
    encryptedTitle = encryptedTitle,
    image = image,
    localImage = null
)