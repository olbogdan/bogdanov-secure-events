package com.bogdanov.android.cryptoevent.presentation.events.list

import androidx.lifecycle.*
import com.bogdanov.domain.di.IoDispatcher
import com.bogdanov.domain.interactors.Resource
import com.bogdanov.domain.interactors.usecases.GetEventsUseCase
import com.bogdanov.android.cryptoevent.presentation.UIState
import com.bogdanov.android.cryptoevent.presentation.events.EventFilter
import com.bogdanov.domain.interactors.EventEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher

class EventsListViewModel
@AssistedInject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @Assisted private val filter: EventFilter,
    private val useCase: GetEventsUseCase
) : ViewModel() {

    val uiState: LiveData<UIState<List<EventEntity>>> = liveData(dispatcher) {
        emit(UIState.Loading)
        emitSource(
            useCase(filter.mapToFilter()).map { resource ->
                when (resource) {
                    is Resource.Loading -> UIState.Loading
                    is Resource.Success -> if (resource.data.isEmpty()) {
                        UIState.Loading
                    } else {
                        UIState.Success(resource.data)
                    }
                    is Resource.Failure -> UIState.Failure(resource.errorMessage)
                }
            }
        )
    }

    class Factory(private val assistedFactory: VMFactory, private val filter: EventFilter) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(filter) as T
        }
    }

    @AssistedFactory
    interface VMFactory {
        fun create(filter: EventFilter): EventsListViewModel
    }

    private fun EventFilter.mapToFilter(): GetEventsUseCase.Filter {
        return when (this) {
            EventFilter.ALL_EVENTS -> GetEventsUseCase.Filter.ALL
            EventFilter.UPCOMING_EVENTS -> GetEventsUseCase.Filter.UPCOMING
        }
    }
}