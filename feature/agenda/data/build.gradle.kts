plugins {
    alias(libs.plugins.sergius.android.feature.ui)
}

android {
    namespace = "com.sergius.agenda.data"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.timber)
    implementation(libs.bundles.koin)
}