import org.evoleq.exposedx.migration.migrations

plugins {
    application
    kotlin("jvm")
    id("io.ktor.plugin") version "2.1.3"
    kotlin("plugin.serialization")
    // id("org.jlleitschuh.gradle.ktlint")//  version "11.0.0"
}

group = project.extra["hanoi.group"] as String
version = project.extra["hanoi.version"] as String
val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val hanoiMainClassName: String = project.extra["hanoiBackend.mainClassName"] as String

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set(hanoiMainClassName)

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    // ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    // serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    // mysql connector
    implementation("mysql", "mysql-connector-java", "8.0.19")

    // h2
    implementation("com.h2database:h2:1.4.200")

    // slf4j
    implementation("org.slf4j:slf4j-nop:2.0.7")
}

migrations(
    "hanoi.towers",
    "db",
    "migrations"
)
/*
tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask> {
    // Adjust the severity level of the no-wildcard-imports rule
    // Set it to "OFF" to disable the rule
    // Set it to "WARN" to allow wildcard imports with a warning
    // Set it to "ERROR" to treat wildcard imports as errors
    rule("no-wildcard-imports") {
        // Set the severity level as needed
        // Possible values: "OFF", "WARN", "ERROR"
        setSeverity("WARN")
    }
}

 */
