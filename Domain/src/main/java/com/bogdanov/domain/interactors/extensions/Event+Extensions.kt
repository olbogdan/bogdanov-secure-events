package com.bogdanov.domain.interactors.extensions

import com.bogdanov.crypto.Crypto
import com.bogdanov.data.local.Event
import com.bogdanov.domain.interactors.EventEntity

internal fun Event.mapToUIEntity(crypto: Crypto): EventEntity {
    return EventEntity(
        id = id,
        creationDate = creationDate,
        dueDate = dueDate,
        description = crypto.decrypt(this.encryptedDescription),
        title = crypto.decrypt(this.encryptedTitle),
        image = localImage
    )
}