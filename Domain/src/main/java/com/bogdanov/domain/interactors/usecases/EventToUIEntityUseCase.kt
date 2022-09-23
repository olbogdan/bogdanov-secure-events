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

class EventToUIEntityUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val crypto: Crypto
) {

    operator fun invoke(value: Resource<Event>): LiveData<Resource<EventEntity>> =
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