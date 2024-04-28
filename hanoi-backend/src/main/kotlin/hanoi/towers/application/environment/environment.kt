package hanoi.towers.application.environment

import io.ktor.server.application.*

fun Application.setupEnvironment(): Environment = with(environment.config) {
    val database = Database(
        url = property("database.url").toString(),
        driver = property("database.driver").toString(),
        user = property("database.user").toString(),
        password = property("database.password").toString()
    )

    val jwt = JWT(
        domain = property("jwt.domain").toString(),
        audience = property("jwt.audience").toString(),
        realm = property("jwt.realm").toString(),
        secret = property("jwt.secret").toString(),
    )

    Environment(
        database,
        jwt,
    )
}

fun Environment.connectToDatabase(): org.jetbrains.exposed.sql.Database = org.jetbrains.exposed.sql.Database.connect(
    database.url,
    database.driver,
    database.user,
    database.password
)

data class Environment(
    val database: Database,
    val jwt: JWT
)
data class JWT(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String
)
data class Database(
    val url: String,
    val driver: String,
    val user: String,
    val password: String
)
