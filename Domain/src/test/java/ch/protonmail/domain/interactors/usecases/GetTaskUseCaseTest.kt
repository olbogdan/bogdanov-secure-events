package ch.protonmail.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ch.protonmail.data.local.TasksDao
import ch.protonmail.data.local.TasksLocalDataSource
import ch.protonmail.data.remote.TasksRemoteDataSource
import ch.protonmail.data.remote.retrofit.TasksApiService
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal class GetTaskUseCaseTest {


    private lateinit var tasksDao: TasksDao
    private lateinit var tasksApiService: TasksApiService
    private lateinit var tasksRemoteDataSource: TasksRemoteDataSource
    private lateinit var tasksLocalDataSource: TasksLocalDataSource


    @Before
    fun setup() {

        tasksApiService = mockk()
        tasksRemoteDataSource = TasksRemoteDataSource(tasksApiService)

        tasksDao = mockk()
        tasksLocalDataSource = TasksLocalDataSource(tasksDao)


    }

    @Test
    operator fun invoke() {
        //todo: finish mocking interfaces for this test

//        val task = GetTaskUseCase().invoke().getOrAwaitValue()
//
//        assertNotNull(task)
    }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    afterObserve.invoke()
    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}