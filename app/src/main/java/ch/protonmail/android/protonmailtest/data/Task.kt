package ch.protonmail.android.protonmailtest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
@Parcelize
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
) : Parcelable
