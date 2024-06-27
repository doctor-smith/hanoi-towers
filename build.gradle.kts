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
    // Version of detekt that will be used. When unspecified the latest detekt
    // version found will be used. Override to stay on the same version.
    toolVersion = "1.23.3"

    // The directories where detekt looks for source files. 
    // Defaults to `files("src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin")`.
    source.setFrom("hanoi-frontend/src", "hanoi-backend/src")

    // Builds the AST in parallel. Rules are always executed in parallel. 
    // Can lead to speedups in larger projects. `false` by default.
    parallel = false

    // Define the detekt configuration(s) you want to use. 
    // Defaults to the default detekt configuration.
    config.setFrom("config/detekt/detekt.yml")

    // Applies the config files on top of detekt's default config file. `false` by default.
    buildUponDefaultConfig = true

    // Turns on all the rules. `false` by default.
    allRules = false

    // Disables all default detekt rulesets and will only run detekt with custom rules
    // defined in plugins passed in with `detektPlugins` configuration. `false` by default.
    disableDefaultRuleSets = false
        
    // Adds debug output during task execution. `false` by default.
    debug = false

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

    //this.jvmTarget = "1.8"
    //jdkHome.set(file("C:/Program Files/Java/jdk-17"))
}
