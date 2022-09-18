buildscript {
    val kotlinVersion = "1.7.10"
    extra["kotlin_version"] = kotlinVersion

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
