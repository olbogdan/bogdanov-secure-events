plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.bogdanov.android.cryptoevent.domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
}

dependencies {
    implementation(project(mapOf("path" to ":SecureEventCrypto")))
    implementation(project(mapOf("path" to ":Data")))

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.lifecycle.livedata)

    testImplementation(libs.bundles.tests)
}