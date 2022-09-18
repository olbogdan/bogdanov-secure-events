package ch.protonmail.android.protonmailtest.crypto

import ch.protonmail.android.crypto.CryptoLib

class ProtonCrypto constructor(private val cryptoLib: CryptoLib) : Crypto {
    private val cache: MutableMap<String, String> = HashMap()

    override fun decrypt(string: String): String {
        val cachedStr = cache[string]
        return if (!cachedStr.isNullOrEmpty()) {
            cachedStr
        } else {
            decryptWithCryptoLib(string).also { cache[string] = it }
        }
    }

    // note: sometimes decrypt fails, retry onFailure mostly returns success
    private fun decryptWithCryptoLib(string: String): String = cryptoLib.decrypt(string).fold(
        onSuccess = { it },
        onFailure = { cryptoLib.decrypt(string).getOrDefault("") }
    )

    override fun encrypt(string: String): String = cryptoLib.decrypt(string).getOrDefault("")
}