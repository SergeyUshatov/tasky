plugins {
    alias(libs.plugins.sergius.android.library)
    alias(libs.plugins.sergius.android.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sergius.core.database"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.bundles.koin)
    implementation(libs.kotlin.serialization.json)
}
