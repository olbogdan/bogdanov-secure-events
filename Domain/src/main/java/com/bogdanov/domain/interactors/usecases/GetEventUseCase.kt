package com.bogdanov.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bogdanov.domain.di.IoDispatcher
import com.bogdanov.domain.interactors.Resource
import com.bogdanov.domain.interactors.EventEntity
import com.bogdanov.domain.interactors.EventsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: EventsRepository,
    private val eventMapper: EventToUIEntityUseCase
) {

    suspend operator fun invoke(id: String): LiveData<Resource<EventEntity>> {
        return liveData(dispatcher) {
            emitSource(repository.getEvent(id))
        }.switchMap { value -> eventMapper(value) }
    }
}