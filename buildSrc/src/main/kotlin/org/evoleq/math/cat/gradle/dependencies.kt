package org.evoleq.math.cat.gradle

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandler.`implementation`(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.`testImplementation`(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.`compile`(dependencyNotation: Any): Dependency? =
    add("compile", dependencyNotation)

fun DependencyHandler.`api`(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

/*
fun DependencyHandlerScope.implementWithOrgAndVersion(
    organization: String,
    version: String,
    packages: Set<String>
){
    packages.forEach {
        implementation("$organization:$it:$version")
    }
}

 */


data class Organisation(
    val name: String,val  scope: DependencyHandlerScope
)

data class OrganisationVersion(
    val name:String,
    val version: String,
    val scope: DependencyHandlerScope
)

fun DependencyHandlerScope.organisation(organisationId: String, conf: Organisation.()->Unit) {
    with(Organisation(organisationId, this)){
        conf()
    }
}

fun Organisation.implementation(dep: String) {
    scope.implementation("$name:$dep")
}

fun Organisation.testImplementation(dep: String) {
    scope.testImplementation("$name:$dep")
}

fun Organisation.compile(dep: String) {
    scope.compile("$name:$dep")
}
fun Organisation.api(dep: String) {
    scope.api("$name:$dep")
}

fun Organisation.version(version: String, configuration: OrganisationVersion.()->Unit) = with(
    OrganisationVersion(
        name,
        version,
        scope
    )
) {
    configuration()
}

fun OrganisationVersion.implementation(artifactId: String) {
    scope.implementation("$name:$artifactId:$version")
}

fun OrganisationVersion.implementations(vararg artifactIds: String) {
    artifactIds.forEach {
        implementation(it)
    }
}

fun OrganisationVersion.testImplementations(vararg artifactIds: String) {
    artifactIds.forEach {
        testImplementation(it)
    }
}

fun OrganisationVersion.testImplementation(artifactId: String) {
    scope.testImplementation("$name:$artifactId:$version")
}

fun OrganisationVersion.compile(artifactId: String) {
    scope.compile("$name:$artifactId:$version")
}

fun OrganisationVersion.api(artifactId: String) {
    scope.api("$name:$artifactId:$version")
}


