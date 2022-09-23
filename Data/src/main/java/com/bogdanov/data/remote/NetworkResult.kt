package com.bogdanov.data.remote

sealed class NetworkResult<out T> {
    data class Success<T : Any>(val data: T) : NetworkResult<T>()
    data class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
    data class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
}