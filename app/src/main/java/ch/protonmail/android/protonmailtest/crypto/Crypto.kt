package ch.protonmail.android.protonmailtest.crypto

interface Crypto {
    fun decrypt(string: String): String
    fun encrypt(string: String): String
}