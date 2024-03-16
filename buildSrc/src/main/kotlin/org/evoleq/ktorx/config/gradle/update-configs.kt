package org.evoleq.ktorx.config.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.withType
import java.io.File

data class UpdateConfigActions(
    val actions: HashSet<File.()->Unit> = hashSetOf()
): Set<File.()->Unit> by actions

inline fun <reified T : Task> Project.updateConfig(
    configPath: String,
    targetPath: String,
    actions: UpdateConfigActions = UpdateConfigActions()//,
    //dependsOnTasks: List<Task> = listOf()
) {
    tasks.withType<T>{
       doLast {
           delete(targetPath)
           copy{
               from(configPath)
               into(targetPath)
           }
           if(actions.isNotEmpty()) {
               fileTree(targetPath).forEach { file ->
                   actions.forEach {
                       file.it()
                   }
               }
           }
       }
    }
}

fun File.update(
    condition: File.()->Boolean,
    action: String.()->String
) {
    if(condition()) {
        with(readLines().map { it.action() }.joinToString("\n") { it }) {
            writeText(this)
        }
    }
}