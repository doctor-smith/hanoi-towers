package hanoi.towers.application.routing

import hanoi.towers.application.environment.Environment
import hanoi.towers.module.db.connectToDB
import hanoi.towers.module.db.schema.User
import hanoi.towers.module.hanoi.routing.hanoi
import hanoi.towers.module.user.routing.user
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun Application.setupRouting(environment: Environment) {
    routing {
        user(environment)
        hanoi(environment)



        get("/hello") {
            /*
            with(connectToDB()) {
                transaction {
                    User.new {
                        username = "${System.currentTimeMillis()}@test.user.net"
                        password = "egal"
                    }
                    commit()
                }
            }
            */
            val user = hanoi.towers.module.user.data.User(

                UUID.randomUUID(),
                "test-username",
                ""
            )
            println(user)
            call.respond(user)
        }
    }
}