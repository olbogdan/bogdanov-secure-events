package ch.protonmail.android.protonmailtest.presentation.tasks

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TaskUIEntity(
    val id: String,
    val creationDate: Date,
    val dueDate: Date,
    val description: String,
    val title: String,
    val image: String?
) : Parcelable