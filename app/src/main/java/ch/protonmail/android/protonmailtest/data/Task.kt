package ch.protonmail.android.protonmailtest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("due_date")
    val dueDate: String,
    @SerializedName("encrypted_description")
    val encryptedDescription: String,
    @SerializedName("encrypted_title")
    val encryptedTitle: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String
) : Parcelable
