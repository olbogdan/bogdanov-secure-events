package com.bogdanov.android.cryptoevent.presentation.events.details

import android.util.Log
import androidx.lifecycle.*
import com.bogdanov.android.cryptoevent.presentation.UIState
import com.bogdanov.domain.di.IoDispatcher
import com.bogdanov.domain.interactors.Resource
import com.bogdanov.domain.interactors.EventEntity
import com.bogdanov.domain.interactors.usecases.DownloadImageUseCase
import com.bogdanov.domain.interactors.usecases.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVE_ARG_KEY = "event"
private const val TAG = "bogdanov"

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getEventsUseCase: GetEventUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    val uiState: LiveData<UIState<EventEntity>> = liveData(dispatcher) {
        val eventFromState = getEventFromState()
        emitSource(
            getEventsUseCase(eventFromState.id).map { resource ->
                when (resource) {
                    is Resource.Loading -> UIState.Loading
                    is Resource.Success -> UIState.Success(resource.data)
                    is Resource.Failure -> UIState.Failure(resource.errorMessage)
                }
            }
        )
    }

    fun downloadImageForEvent(eventId: String) {
        viewModelScope.launch(dispatcher) {
            when (downloadImageUseCase(eventId)) {
                is Resource.Success -> Log.d(TAG, "downloadImageForEvent started")
                is Resource.Failure -> Log.d(TAG, "downloadImageForEvent failed")
                is Resource.Loading -> Log.d(TAG, "downloadImageForEvent loading")
            }
        }
    }

    private fun getEventFromState(): EventEntity {
        return state.get<EventEntity>(SAVE_ARG_KEY)
            ?: throw IllegalStateException("SavedStateHandle expected to have $SAVE_ARG_KEY argument")
    }
}