package ch.protonmail.domain.interactors.usecases

import android.content.Context
import ch.protonmail.data.local.Task
import ch.protonmail.domain.interactors.Resource
import ch.protonmail.domain.interactors.TasksRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import javax.inject.Inject


class DownloadImageUseCase @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: TasksRepository,
) {

    suspend operator fun invoke(taskId: String): Resource<Unit> {
        val task: Task? = repository.getTaskImmediate(taskId)
        try {
            if (task != null) {
                val directory = File(appContext.filesDir, "images")
                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val outputFileName =
                    directory.path + task.image.substring(task.image.lastIndexOf('/'))
                downloadFile(URL(task.image), outputFileName)
                repository.updateTask(task.copy(localImage = outputFileName))
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