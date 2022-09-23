package com.bogdanov.android.cryptoevent.presentation

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Failure(val errorMessage: String) : UIState<Nothing>()
}