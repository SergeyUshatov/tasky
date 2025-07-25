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
    implementation(projects.core.domain)

    implementation(libs.timber)

    testImplementation(libs.google.truth)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.cash.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
}