/*
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" apply(false)
}
*/

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    google()
}
