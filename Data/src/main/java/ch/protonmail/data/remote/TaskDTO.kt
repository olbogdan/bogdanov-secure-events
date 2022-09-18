package ch.protonmail.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class TaskDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("creation_date")
    val creationDate: Date,
    @SerializedName("due_date")
    val dueDate: Date,
    @SerializedName("encrypted_description")
    val encryptedDescription: String,
    @SerializedName("encrypted_title")
    val encryptedTitle: String,
    @SerializedName("image")
    val image: String
)
