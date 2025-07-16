import com.android.build.api.dsl.LibraryExtension
import com.sergius.convention.ExtensionType
import com.sergius.convention.configureBuildTypes
import com.sergius.convention.configureKotlinAndroid
import com.sergius.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )

                defaultConfig {
                    testInstrumentationRunner = "com.sergius.common.android_test.KoinTestRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                "testImplementation"(kotlin("test"))
                "testImplementation"(libs.findLibrary("androidx.test.runner").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.api").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.engine").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.params").get())
                "testImplementation"(libs.findLibrary("mockk").get())
                "testImplementation"(libs.findLibrary("kotlinx.coroutines.test").get())
                "testImplementation"(libs.findLibrary("androidx.arch.core.test").get())
                "testImplementation"(libs.findLibrary("cash.turbine").get())
            }
        }
    }
}