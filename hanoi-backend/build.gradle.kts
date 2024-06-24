import org.evoleq.exposedx.migration.migrations

plugins {
    jacoco
    application
    kotlin("jvm")
    id("io.ktor.plugin") version "2.1.3"
    kotlin("plugin.serialization")
}

jacoco {
  toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
  dependsOn(tasks.test)
  reports {
    xml.required.set(true)   // Generiere XML-Bericht
    html.required.set(true)  // Generiere HTML-Bericht
  }
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
    implementation("mysql", "mysql-connector-java","8.0.19")

    // h2
    implementation("com.h2database:h2:1.4.200")

    // slf4j
    implementation ("org.slf4j:slf4j-nop:2.0.7")
}

migrations(
    "hanoi.towers",
    "db",
    "migrations"
)
