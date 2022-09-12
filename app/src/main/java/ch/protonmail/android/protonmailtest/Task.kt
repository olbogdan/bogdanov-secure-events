package ch.protonmail.android.protonmailtest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("creation_date")
    val creationDate: String,
    @SerialName("due_date")
    val dueDate: String,
    @SerialName("encrypted_description")
    val encryptedDescription: String,
    @SerialName("encrypted_title")
    val encryptedTitle: String,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: String
)
