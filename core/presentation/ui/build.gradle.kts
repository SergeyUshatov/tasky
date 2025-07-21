plugins {
    alias(libs.plugins.sergius.android.library.compose)
}

android {
    namespace = "com.sergius.core.presentation.ui"
}

dependencies {
    implementation(projects.core.domain)
}