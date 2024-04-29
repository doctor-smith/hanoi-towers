import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    // id("org.jlleitschuh.gradle.ktlint") // version "11.0.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

group = project.extra["hanoi.group"] as String
version = project.extra["hanoi.version"] as String
val kotlinxCoroutinesCore: String by project
val composeCompiler: String by project
val ktorClientCoreJs: String by project
val ktorClientJs: String by project
val ktorVersion: String by project

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            kotlin.srcDir("src/jsMain/kotlin")
            resources.srcDir("src/main/resources")

            dependencies {
                // kotlin coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesCore")

                // ktor client
                implementation("io.ktor:ktor-client-core:$ktorClientCoreJs")
                implementation("io.ktor:ktor-client-js:$ktorClientJs")

                // Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                // implementation("io.ktor:ktor-client-serialization:$ktorVersion")

                // dotenv
                implementation(npm("dotenv", "16.0.1"))

                // compose
                implementation(compose.html.core)
                implementation(compose.runtime)
            }
        }

        val jsTest by getting {
            kotlin.srcDir("src/jsTest/kotlin")

            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesCore")
                implementation(kotlin("test-js"))
                implementation(compose.html.testUtils)
            }
        }
/*
        val uiTest by getting {
            kotlin.srcDir("src/uiTest/kotlin")

            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesCore")
                implementation(kotlin("test-js"))
                implementation(compose.html.testUtils)
            }
        }
 */
    }
}

compose {
    // kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:$composeCompiler")
}

// a temporary workaround for a bug in jsRun invocation - see https://youtrack.jetbrains.com/issue/KT-48273
afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        nodeVersion = "16.0.0"
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}
