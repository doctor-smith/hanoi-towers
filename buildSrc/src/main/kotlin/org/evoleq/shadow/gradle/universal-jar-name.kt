package org.evoleq.shadow.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.withType
import java.io.File

inline fun <reified T : Task> Project.prepareDeployment() {
    val sourceFileName = "${buildDir}/libs/${name}-${version}-all.jar"
    val deploymentDir = "${buildDir}/../../deployment/home/solyton/${name}/build/libs"
    tasks.withType<T> {
        doLast{
            //delete(sourceFileName)
            delete("$deploymentDir/server.jar")
            copy {
                from(sourceFileName){
                    rename{
                        "server.jar"
                    }
                }
                into(deploymentDir)
            }
            
            
        }
    }
}

inline fun <reified T : Task> Project.prepareTestDeployment() {
    val sourceFileName = "${buildDir}/libs/${name}-${version}-all.jar"
    val deploymentDir = "${buildDir}/../../deployment/home/solyton-test/${name}/build/libs"
    tasks.withType<T> {
        doLast{
            //delete(sourceFileName)
            delete("$deploymentDir/server.jar")
            copy {
                from(sourceFileName){
                    rename{
                        "server.jar"
                    }
                }
                into(deploymentDir)
            }
            
            
        }
    }
}

inline fun <reified T : Task> Project.prepareJsDeployment(doIt: Boolean = false) {
    
    val sourceFileName = "${buildDir}/distributions/${name}.js"
    val deploymentDir = "${buildDir}/../../deployment/home/solyton/${name}/build/distributions"
    if(doIt) {
        tasks.withType<T> {
            doLast {
                delete(deploymentDir)
                copy {
                    from(sourceFileName) {
                        rename {
                            "server.js"
                        }
                    }
                    into(deploymentDir)
                }
                delete("$deploymentDir/server.js.map")
                copy {
                    from("$sourceFileName.map") {
                        rename {
                            "server.js.map"
                        }
                    }
                    into(deploymentDir)
                }
                copy {
                    from("${buildDir}/distributions/index.html")
                    into(deploymentDir)
                }
                copy {
                    from("${buildDir}/distributions/css")
                    into("$deploymentDir/css")
                }
                copy {
                    from("${buildDir}/distributions/img")
                    into("$deploymentDir/img")
                }
                copy {
                    from("${buildDir}/distributions/js")
                    into("$deploymentDir/js")
                }
                copy {
                    from("${buildDir}/distributions/video")
                    into("$deploymentDir/video")
                }
                File(deploymentDir+"/index.html").replaceEnv("prod")
            }
        }
    }
}

inline fun <reified T : Task> Project.prepareTestJsDeployment(doIt: Boolean = true) {
    val sourceFileName = "${buildDir}/distributions/${name}.js"
    val deploymentDir = "${buildDir}/../../deployment/home/solyton-test/${name}/build/distributions"
    if(doIt) {
        tasks.withType<T> {
            doLast {
                delete("$deploymentDir")
                copy {
                    from(sourceFileName) {
                        rename {
                            "server.js"
                        }
                    }
                    into(deploymentDir)
                }
                delete("$deploymentDir/server.js.map")
                copy {
                    from("$sourceFileName.map")
                    from("$sourceFileName.map") {
                        rename {
                            "server.js.map"
                        }
                    }
                    into(deploymentDir)
                }
                copy {
                    from("${buildDir}/distributions/index.html")
                    into(deploymentDir)
                }
                copy {
                    from("${buildDir}/distributions/css")
                    into("$deploymentDir/css")
                }
                copy {
                    from("${buildDir}/distributions/img")
                    into("$deploymentDir/img")
                }
                copy {
                    from("${buildDir}/distributions/js")
                    into("$deploymentDir/js")
                }
                copy {
                    from("${buildDir}/distributions/video")
                    into("$deploymentDir/video")
                }
                File(deploymentDir+"/index.html").replaceEnv("test")
            }
        }
    }
}
fun File.replaceEnv(targetEnv:String) {
    val text = readText()
    val oldEnv = when {
        text.contains("return \"dev\";") -> "dev"
        text.contains("return \"prod\";") -> "prod"
        text.contains("return \"staging\";") -> "staging"
        else -> "test"
    }
    writeText(text.replace("return \"$oldEnv\";", "return \"$targetEnv\";"))
}