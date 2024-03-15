import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform")// version "1.7.20"
    id("org.jetbrains.compose")// version "1.2.2"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

val kotlinxCoroutinesCore:String by project
val composeCompiler:String by project
val ktorClientCoreJs:String by project
val ktorClientJs:String by project

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")

            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesCore") // 1.6.4
                implementation("io.ktor:ktor-client-core-js:$ktorClientCoreJs")
                implementation("io.ktor:ktor-client-js:$ktorClientJs")

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



