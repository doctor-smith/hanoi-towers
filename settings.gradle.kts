rootProject.name = "hanoi-towers"

include(":hanoi-frontend")
include(":hanoi-backend")
include(":hanoi-database")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        // maven ("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String) apply false
        id("org.jetbrains.compose").version(extra["compose.version"] as String) apply false
        kotlin("plugin.serialization").version(extra["kotlin.version"] as String) apply false
        kotlin("jvm").version(extra["kotlin.version"] as String) apply false
        // id("io.ktor.plugin").version(extra["io.ktor.plugin"] as String) apply false
    }
}
