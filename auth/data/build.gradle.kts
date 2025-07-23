plugins {
    alias(libs.plugins.sergius.android.library)
}

android {
    namespace = "com.sergius.auth.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}