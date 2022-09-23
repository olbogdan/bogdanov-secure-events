package com.bogdanov.domain.interactors

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EventEntity(
    val id: String,
    val creationDate: Date,
    val dueDate: Date,
    val description: String,
    val title: String,
    val image: String?
) : Parcelable