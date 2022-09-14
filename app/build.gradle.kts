plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "ch.protonmail.android.protonmailtest"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    namespace = "ch.protonmail.android.protonmailtest"
}

//todo: extract dependencies into separate Dependencies.kt and Version.kt files
dependencies {
    // Android
    implementation("androidx.fragment:fragment-ktx:1.5.2")
    implementation("com.google.android.material:material:1.6.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // Networking
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    // DI
    kapt("com.google.dagger:hilt-android-compiler:2.42")
    implementation("com.google.dagger:hilt-android:2.42")

    // Cryptography
    implementation(files("../libs/crypto.aar"))
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
