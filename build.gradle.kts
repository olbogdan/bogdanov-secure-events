//Suppress until fixed: https://youtrack.jetbrains.com/issue/KTIJ-19369/False-positive-cant-be-called-in-this-context-by-implicit-receiver-with-plugins-in-Gradle-version-catalogs-as-a-TOML-file
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.plugin.hilt) apply false
    alias(libs.plugins.plugin.kotlin) apply false
    alias(libs.plugins.plugin.navigation) apply false
    alias(libs.plugins.plugin.android.application) apply false
    alias(libs.plugins.plugin.android.library) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
