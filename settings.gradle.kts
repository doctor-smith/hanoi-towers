rootProject.name = "hanoi-towers"

include(":hanoi-frontend")
include(":hanoi-backend")
include(":hanoi-database")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String) apply false
        id("org.jetbrains.compose").version(extra["compose.version"] as String) apply false
    }
}
