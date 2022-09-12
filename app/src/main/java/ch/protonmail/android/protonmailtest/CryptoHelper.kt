package ch.protonmail.android.protonmailtest

import ch.protonmail.android.crypto.CryptoLib
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoHelper @Inject constructor(
) {
    var instance: CryptoLib = CryptoLib()
}