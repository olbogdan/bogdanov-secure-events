package com.bogdanov.domain.interactors.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bogdanov.data.local.EventDao
import com.bogdanov.data.local.EventsLocalDataSource
import com.bogdanov.data.remote.EventRemoteDataSource
import com.bogdanov.data.remote.retrofit.EventApiService
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal class GetEventUseCaseTest {


    private lateinit var eventDao: EventDao
    private lateinit var eventApiService: EventApiService
    private lateinit var eventRemoteDataSource: EventRemoteDataSource
    private lateinit var eventsLocalDataSource: EventsLocalDataSource


    @Before
    fun setup() {

        eventApiService = mockk()
        eventRemoteDataSource = EventRemoteDataSource(eventApiService)

        eventDao = mockk()
        eventsLocalDataSource = EventsLocalDataSource(eventDao)


    }

    @Test
    operator fun invoke() {
        //todo: finish mocking interfaces for this test

//        val event = GetEventUseCase().invoke().getOrAwaitValue()
//
//        assertNotNull(event)
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