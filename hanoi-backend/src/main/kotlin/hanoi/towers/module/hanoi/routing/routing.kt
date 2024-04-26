package hanoi.towers.module.hanoi.routing

import hanoi.towers.application.environment.Environment
import io.ktor.server.routing.*
import io.ktor.util.*

@KtorDsl
fun Routing.hanoi(environment: Environment) {
    post("hanoi/create") {

    }
}