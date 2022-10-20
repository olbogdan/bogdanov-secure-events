plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.bogdanov.android.cryptoevent.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
}

dependencies {
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}