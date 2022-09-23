plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 33
    namespace = "com.bogdanov.android.cryptoevent"
    defaultConfig {
        applicationId = "com.bogdanov.android.cryptoevent"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        viewBinding = true
    }
}

//todo: extract dependencies into separate Dependencies.kt and Version.kt files for better structure and reuse between modules
dependencies {
    // Android
    implementation("androidx.fragment:fragment-ktx:1.5.2")
    implementation("com.google.android.material:material:1.6.1")

    // Navigation
    val navigationVersion = "2.5.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // DI
    val hiltVersion = "2.43.2"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // KTX
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    // Domain
    implementation(project(mapOf("path" to ":Domain")))

    // UI
    implementation("com.squareup.picasso:picasso:2.71828")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
