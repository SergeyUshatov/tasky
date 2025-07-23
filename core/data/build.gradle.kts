plugins {
    alias(libs.plugins.sergius.android.library)
    alias(libs.plugins.sergius.jvm.ktor)
}

android {
    namespace = "com.sergius.core.data"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.timber)
    implementation(libs.bundles.koin)
}