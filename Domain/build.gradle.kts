plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "ch.protonmail.domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
}

dependencies {
    implementation(project(mapOf("path" to ":ProtonCrypto")))
    implementation(project(mapOf("path" to ":Data")))
    // DI
    val hiltVersion = "2.42"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // KTX
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
}