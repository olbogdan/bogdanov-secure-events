plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 33
    namespace = "ch.protonmail.android.protonmailtest"
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
}

//todo: extract dependencies into separate Dependencies.kt and Version.kt files for better structure
dependencies {
    // Android
    implementation("androidx.fragment:fragment-ktx:1.5.2")
    implementation("com.google.android.material:material:1.6.1")

    // Navigation
    val navigationVersion = "2.5.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // DI
    val hiltVersion = "2.42"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // KTX
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    // Cryptography
    implementation(project(mapOf("path" to ":ProtonCrypto")))

    // Data
    implementation(project(mapOf("path" to ":Data")))
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
