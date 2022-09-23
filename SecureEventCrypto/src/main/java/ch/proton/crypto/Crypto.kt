package ch.proton.crypto

interface Crypto {
    fun decrypt(string: String): String
}