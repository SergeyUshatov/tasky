plugins {
    alias(libs.plugins.sergius.android.library)
    alias(libs.plugins.sergius.android.room)
}

android {
    namespace = "com.sergius.core.database"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.bundles.koin)
}
