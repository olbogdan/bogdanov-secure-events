package com.bogdanov.data.remote

import com.bogdanov.data.remote.retrofit.EventApiService

//todo: reduce sever calls by ETags, Conditional Caching, joining to pull or running requests
class EventRemoteDataSource(private val api: EventApiService) {
    suspend fun getEvents(): NetworkResult<List<EventDTO>> = api.getEvents()
}