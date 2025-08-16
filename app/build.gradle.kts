plugins {
    alias(libs.plugins.sergius.android.application.compose)
    alias(libs.plugins.sergius.android.application.jacoco)
    alias(libs.plugins.sergius.jvm.ktor)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

android {
    namespace = "com.sergius.tasky"

    defaultConfig {
        testInstrumentationRunner = "com.sergius.tasky.KoinTestRunner"
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.data)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.auth.data)
    implementation(projects.auth.presentation)
    implementation(projects.feature.agenda.presentation)

    implementation(libs.bundles.koin)

    // Logging
    implementation(libs.timber)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.lifecycle.navigation3)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}