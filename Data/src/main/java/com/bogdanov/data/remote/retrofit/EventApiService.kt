package com.bogdanov.data.remote.retrofit

import com.bogdanov.data.remote.NetworkResult
import com.bogdanov.data.remote.EventDTO
import retrofit2.http.GET

interface EventApiService {

    @GET("/tasks")
    suspend fun getEvents(): NetworkResult<List<EventDTO>>
}