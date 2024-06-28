plugins {
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    google()
}

detekt {
    // Define Version of detekt that will be used
    toolVersion = "1.23.3"

    // Define directories where detekt looks for source files
    source.setFrom("hanoi-frontend/src", "hanoi-backend/src")

    // Define the detekt configuration(s) that should be used
    config.setFrom("config/detekt/detekt.yml")

    // If set to `true` the build does not fail when the
    // maxIssues count was reached. Defaults to `false`.
    ignoreFailures = true

    // Specify the base path for file paths in the formatted reports. 
    // If not set, all file paths reported will be absolute file path.
    basePath = projectDir.absolutePath
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        // Enable/Disable XML report (default: true)
        xml.required.set(true)
        xml.outputLocation.set(file("build/reports/detekt.xml"))

        // Enable/Disable HTML report (default: true)
        html.required.set(true)
        html.outputLocation.set(file("build/reports/detekt.html"))

        // Enable/Disable TXT report (default: true)
        txt.required.set(true)
        txt.outputLocation.set(file("build/reports/detekt.txt"))

        // Enable/Disable SARIF report (default: false)
        sarif.required.set(true)
        sarif.outputLocation.set(file("build/reports/detekt.sarif"))

        // Enable/Disable MD report (default: false)
        md.required.set(true)
        md.outputLocation.set(file("build/reports/detekt.md"))
    }
}
