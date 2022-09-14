package ch.protonmail.android.protonmailtest.presentation.tasks

enum class TaskFilter(val value: Int) {
    ALL_TASKS(0),
    UPCOMING_TASKS(1);

    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}