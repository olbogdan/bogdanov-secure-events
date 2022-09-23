package com.bogdanov.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bogdanov.crypto.Crypto
import com.bogdanov.data.local.Event
import com.bogdanov.domain.di.IoDispatcher
import com.bogdanov.domain.interactors.Resource
import com.bogdanov.domain.interactors.EventEntity
import com.bogdanov.domain.interactors.extensions.mapToUIEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EventsListToUIEntityUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val crypto: Crypto
) {

    operator fun invoke(resource: Resource<List<Event>>): LiveData<Resource<List<EventEntity>>> =
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