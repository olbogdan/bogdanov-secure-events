package com.bogdanov.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bogdanov.data.local.Event
import com.bogdanov.data.local.EventsLocalDataSource
import com.bogdanov.data.remote.NetworkResult
import com.bogdanov.data.remote.EventDTO
import com.bogdanov.data.remote.EventRemoteDataSource
import com.bogdanov.domain.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

/**
 * Repository works as mediator between Remote and Local data sources.
 * Local datasource consider to be a singe source of true, LiveData notified with dynamic changes.
 * The Remote data is never directly transferred to the user, it is stored into localDataSource.
 * The Remote (Network) fails may be delivered to the user via Resource.Failure().
 */
class EventsRepository @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val remoteDataSource: EventRemoteDataSource,
    private val localDataSource: EventsLocalDataSource
) {

    fun getAllEvents(): LiveData<Resource<List<Event>>> = performGetOperation(
        databaseQuery = { localDataSource.getAllEvents() },
        remoteCall = { remoteDataSource.getEvents() },
        saveCallResult = { localDataSource.updateEvents(it.map { dto -> dto.toEvent() }) }
    )

    fun getUpcomingEvents(targetDate: Date): LiveData<Resource<List<Event>>> = performGetOperation(
        databaseQuery = { localDataSource.getUpcomingEvents(targetDate) },
        remoteCall = { remoteDataSource.getEvents() },
        saveCallResult = { localDataSource.updateEvents(it.map { dto -> dto.toEvent() }) }
    )

    fun getEvent(id: String): LiveData<Resource<Event>> = liveData(dispatcher) {
        emit(Resource.Loading)
        val resource: LiveData<Resource<Event>> = localDataSource.getEvent(id)
            .map { Resource.Success(it) }
        emitSource(resource)
    }

    suspend fun getEventImmediate(id: String): Event? = localDataSource.getEventImmediate(id)

    suspend fun updateEvent(event: Event) {
        localDataSource.updateEvent(event)
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

private fun EventDTO.toEvent(): Event = Event(
    id = id,
    creationDate = creationDate,
    dueDate = dueDate,
    encryptedDescription = encryptedDescription,
    encryptedTitle = encryptedTitle,
    image = image,
    localImage = null
)