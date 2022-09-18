package ch.protonmail.domain.interactors

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TaskEntity(
    val id: String,
    val creationDate: Date,
    val dueDate: Date,
    val description: String,
    val title: String,
    val image: String?
) : Parcelable