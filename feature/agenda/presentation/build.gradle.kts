plugins {
    alias(libs.plugins.sergius.android.feature.ui)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.sergius.agenda.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
}