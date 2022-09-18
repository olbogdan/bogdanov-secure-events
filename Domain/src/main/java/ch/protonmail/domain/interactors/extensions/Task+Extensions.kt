package ch.protonmail.domain.interactors.extensions

import ch.proton.crypto.Crypto
import ch.protonmail.domain.interactors.TaskEntity

internal fun ch.protonmail.data.local.Task.mapToUIEntity(crypto: Crypto): TaskEntity {
    return TaskEntity(
        id = id,
        creationDate = creationDate,
        dueDate = dueDate,
        description = crypto.decrypt(this.encryptedDescription),
        title = crypto.decrypt(this.encryptedTitle),
        image = localImage
    )
}