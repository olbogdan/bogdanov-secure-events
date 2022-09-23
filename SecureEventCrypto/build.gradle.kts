plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "ch.proton.crypto"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
}

dependencies {
    // DI
    val hiltVersion = "2.43.2"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
}