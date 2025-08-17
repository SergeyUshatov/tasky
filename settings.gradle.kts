pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "tasky"
include(":app")
include(":core:domain")
include(":core:presentation:ui")
include(":core:presentation:designsystem")
include(":auth:presentation")
include(":auth:domain")
include(":auth:data")
include(":core:data")
include(":common:android-test")
include(":feature:agenda:presentation")
include(":core:database")
