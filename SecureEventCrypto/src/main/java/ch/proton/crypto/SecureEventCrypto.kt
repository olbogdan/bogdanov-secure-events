package ch.proton.crypto

class SecureEventCrypto : Crypto {
    private val cache: MutableMap<String, String> = HashMap()

    override fun decrypt(string: String): String {
        val cachedStr = cache[string]
        return if (!cachedStr.isNullOrEmpty()) {
            cachedStr
        } else {
            //imagine we are decrypting by some mechanizm
            string
        }
    }
}