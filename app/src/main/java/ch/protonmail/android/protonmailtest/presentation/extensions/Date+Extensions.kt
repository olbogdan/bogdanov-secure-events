package ch.protonmail.android.protonmailtest.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toReadable(): String =
    SimpleDateFormat("yyyy-MM-dd • hh:mm", Locale.getDefault()).format(this)
