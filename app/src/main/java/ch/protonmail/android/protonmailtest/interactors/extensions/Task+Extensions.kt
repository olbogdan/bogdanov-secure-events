package ch.protonmail.android.protonmailtest.interactors.extensions

import ch.proton.crypto.Crypto
import ch.protonmail.data.local.Task
import ch.protonmail.android.protonmailtest.presentation.tasks.TaskUIEntity

internal fun ch.protonmail.data.local.Task.mapToUIEntity(crypto: Crypto): TaskUIEntity {
    return TaskUIEntity(
        id = id,
        creationDate = creationDate,
        dueDate = dueDate,
        description = crypto.decrypt(this.encryptedDescription),
        title = crypto.decrypt(this.encryptedTitle),
        image = localImage
    )
}