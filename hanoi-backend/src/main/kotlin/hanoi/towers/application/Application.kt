package hanoi.towers.application


import hanoi.towers.application.environment.setupEnvironment
import hanoi.towers.application.routing.setupRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*


fun Application.hanoi() {

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowHeaders)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.AccessControlAllowMethods)
        allowCredentials = true
        anyHost()
        maxAgeInSeconds = 24 * 3600 // 1 day
    }
    install(ContentNegotiation) {
        json()
    }
    val environment = setupEnvironment()
    setupRouting(environment)
}