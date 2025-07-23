plugins {
    alias(libs.plugins.sergius.android.feature.ui)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.sergius.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.auth.data)

    implementation(libs.timber)
}