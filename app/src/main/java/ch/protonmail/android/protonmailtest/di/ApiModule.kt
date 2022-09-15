package ch.protonmail.android.protonmailtest.di

import ch.protonmail.android.protonmailtest.data.remote.TasksRemoteRepository
import ch.protonmail.android.protonmailtest.data.remote.retrofit.NetworkResultCallAdapterFactory
import ch.protonmail.android.protonmailtest.data.remote.retrofit.TasksApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BASE_URL = "https://632238cc362b0d4e7dcabbc7.mockapi.io/protontesttask/"
//    "https://proton-android-testcloud.europe-west1.firebasedatabase.app"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .callTimeout(
                2,
                TimeUnit.MINUTES
            ) //todo: remove timeouts, added because my connection unstable
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideTasksApiService(retrofit: Retrofit): TasksApiService =
        retrofit.create(TasksApiService::class.java)

    @Singleton
    @Provides
    fun providesTasksRemoteRepository(apiService: TasksApiService) =
        TasksRemoteRepository(apiService)
}


// Workaround of CertPathValidatorException Emulator API 23
// Unsafe clint have to be used for Emulator API 23 & API 24

//private fun getUnsafeOkHttpClient(): OkHttpClient? {
//    return try {
//        // Create a trust manager that does not validate certificate chains
//        val trustAllCerts = arrayOf<TrustManager>(
//            object : X509TrustManager {
//                @Throws(CertificateException::class)
//                override fun checkClientTrusted(
//                    chain: Array<X509Certificate?>?,
//                    authType: String?
//                ) {
//                }
//
//                @Throws(CertificateException::class)
//                override fun checkServerTrusted(
//                    chain: Array<X509Certificate?>?,
//                    authType: String?
//                ) {
//                }
//
//                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
//                    return arrayOf()
//                }
//            }
//        )
//
//        // Install the all-trusting trust manager
//        val sslContext = SSLContext.getInstance("SSL")
//        sslContext.init(null, trustAllCerts, SecureRandom())
//        // Create an ssl socket factory with our all-trusting manager
//        val sslSocketFactory = sslContext.socketFactory
//        val trustManagerFactory: TrustManagerFactory =
//            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
//        trustManagerFactory.init(null as KeyStore?)
//        val trustManagers: Array<TrustManager> =
//            trustManagerFactory.trustManagers
//        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
//            "Unexpected default trust managers:" + trustManagers.contentToString()
//        }
//
//        val trustManager =
//            trustManagers[0] as X509TrustManager
//
//
//        val builder = OkHttpClient.Builder()
//        builder.sslSocketFactory(sslSocketFactory, trustManager)
//        builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
//        builder.build()
//    } catch (e: Exception) {
//        throw RuntimeException(e)
//    }
//}