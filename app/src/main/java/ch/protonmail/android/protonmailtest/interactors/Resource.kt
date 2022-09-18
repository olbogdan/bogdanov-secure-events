package ch.protonmail.android.protonmailtest.interactors

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val errorMessage: String) : Resource<Nothing>()
}