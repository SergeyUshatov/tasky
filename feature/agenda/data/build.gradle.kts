plugins {
    alias(libs.plugins.sergius.android.feature.ui)
    alias(libs.plugins.sergius.jvm.ktor)
}

android {
    namespace = "com.sergius.agenda.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.timber)
    implementation(libs.bundles.koin)
}