plugins {
    alias(libs.plugins.sergius.android.library.compose)
}

android {
    namespace = "com.sergius.core.presentation.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.timber)

    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.material)
    api(libs.androidx.compose.material3)
}