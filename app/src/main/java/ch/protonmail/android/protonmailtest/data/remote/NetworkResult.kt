package ch.protonmail.android.protonmailtest.data.remote

//todo: change to data classes like in UIState
sealed class NetworkResult<out T> {
    data class Success<T : Any>(val data: T) : NetworkResult<T>()
    data class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
    data class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
}