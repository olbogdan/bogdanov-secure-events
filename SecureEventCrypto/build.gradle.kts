plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.bogdanov.android.cryptoevent.crypto"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
}

dependencies {
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}