package ch.protonmail.android.protonmailtest.presentation
// todo: do we need any?  hmm. need to test
sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Failure(val errorMessage: String) : UIState<Nothing>()
}