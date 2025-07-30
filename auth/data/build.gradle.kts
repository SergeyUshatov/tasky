plugins {
    alias(libs.plugins.sergius.android.library)
    alias(libs.plugins.sergius.jvm.ktor)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.sergius.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.bundles.koin)

    androidTestImplementation(projects.common.androidTest)
}