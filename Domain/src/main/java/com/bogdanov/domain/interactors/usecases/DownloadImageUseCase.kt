package com.bogdanov.domain.interactors.usecases

import android.content.Context
import com.bogdanov.data.local.Event
import com.bogdanov.domain.interactors.Resource
import com.bogdanov.domain.interactors.EventsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import javax.inject.Inject


class DownloadImageUseCase @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(eventId: String): Resource<Unit> {
        val event: Event? = repository.getEventImmediate(eventId)
        try {
            if (event != null) {
                val directory = File(appContext.filesDir, "images")
                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val outputFileName =
                    directory.path + event.image.substring(event.image.lastIndexOf('/'))
                downloadFile(URL(event.image), outputFileName)
                repository.updateEvent(event.copy(localImage = outputFileName))
                return Resource.Success(Unit)
            }
        } catch (exception: Exception) {
            return Resource.Failure("${exception.message}")
        }
        return Resource.Failure("DownloadImageUseCase undefined state")
    }

    private fun downloadFile(url: URL, outputFileName: String) {
        return url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(outputFileName).use { fos ->
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                }
            }
        }
    }
}