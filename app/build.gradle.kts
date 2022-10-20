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

dependencies {
    implementation(libs.android.material)

    implementation(libs.bundles.navigation)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.lifecycle.livedata)
    implementation(libs.picasso)

    implementation(project(mapOf("path" to ":Domain")))
}

// Allow references to generated code.
kapt {
    correctErrorTypes = true
}

// Dedicate Hilt annotation processor in a dedicated Gradle task. Reduce incremental compilation time.
hilt {
    enableAggregatingTask = true
}
