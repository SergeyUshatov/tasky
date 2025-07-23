plugins {
    alias(libs.plugins.sergius.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}
