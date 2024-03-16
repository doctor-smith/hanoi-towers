package hanoi.towers

import hanoi.towers.application.Application

const val maxNumberOfSlices = 10

fun main() {
    val env = js("PROCESS_ENV")
    console.log("env = $env")
    console.log("MEANING_OF_LIFE = ${env.MEANING_OF_LIFE}")

    Application()
}


