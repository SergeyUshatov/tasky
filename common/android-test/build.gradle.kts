plugins {
    alias(libs.plugins.sergius.android.library)
}

android {
    namespace = "com.sergius.common.anroidtest"
}

dependencies {
    api(libs.androidx.junit)
    api(libs.android.junit4.params)
    api(libs.androidx.test.runner)
    api(libs.koin.android)
    api(libs.koin.test)
    api(libs.koin.test.junit4)
    api(libs.google.truth)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.test.junit4)
}