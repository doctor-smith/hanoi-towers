package hanoi.towers.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.setupRouting() {
    routing {
        get("/hello") {
            call.respondText("selber hello")
        }
    }
}