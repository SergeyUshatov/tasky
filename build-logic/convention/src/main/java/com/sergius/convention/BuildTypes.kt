package com.sergius.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import kotlin.run

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
        val baseUrl = gradleLocalProperties(rootDir, providers).getProperty("BASE_URL")

        val props = mapOf(
            "baseUrl" to baseUrl,
        )

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                            configureDebugBuildType(props)

                            enableAndroidTestCoverage = true
                            enableUnitTestCoverage = true
                        }

                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }

            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                            configureDebugBuildType(props)
                        }

                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                            configureReleaseBuildType(props)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(props: Map<String, Any>) {
    buildConfigField("String", "BASE_URL", "\"${props["baseUrl"]}\"")
}

private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
}

private fun BuildType.configureReleaseBuildType(props: Map<String, Any>) {
    buildConfigField("String", "BASE_URL", "\"${props["baseUrl"]}\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}