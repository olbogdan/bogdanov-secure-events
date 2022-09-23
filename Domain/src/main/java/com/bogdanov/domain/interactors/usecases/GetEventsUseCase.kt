package com.bogdanov.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bogdanov.domain.di.IoDispatcher
import com.bogdanov.domain.interactors.Resource
import com.bogdanov.domain.interactors.EventEntity
import com.bogdanov.domain.interactors.EventsRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject


class GetEventsUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: EventsRepository,
    private val eventsMapper: EventsListToUIEntityUseCase
) {

    operator fun invoke(filter: Filter): LiveData<Resource<List<EventEntity>>> {
        return liveData(dispatcher) {
            val resource = when (filter) {
                is Filter.ALL -> repository.getAllEvents()
                is Filter.UPCOMING -> repository.getUpcomingEvents(Date())
            }.distinctUntilChanged()
            emitSource(resource)
        }.switchMap { value ->
            liveData(dispatcher) {
                emitSource(eventsMapper(value))
            }
        }
    }

    sealed class Filter {
        object ALL : Filter()
        object UPCOMING : Filter()
    }
}