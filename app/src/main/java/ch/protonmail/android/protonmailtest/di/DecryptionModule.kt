package ch.protonmail.android.protonmailtest.di

import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.crypto.Crypto
import ch.protonmail.android.protonmailtest.crypto.ProtonCrypto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DecryptionModule {

    @Singleton
    @Provides
    fun provideCryptoLib(): Crypto = ProtonCrypto(CryptoLib())
}



