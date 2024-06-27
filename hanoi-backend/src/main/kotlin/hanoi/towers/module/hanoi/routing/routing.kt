package hanoi.towers.module.hanoi.routing

import hanoi.towers.application.environment.Environment
import hanoi.towers.module.hanoi.action.moveSlice
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.evoleq.math.state.runOn
import org.evoleq.util.Base

@KtorDsl
fun Routing.hanoi(environment: Environment) {
    post("hanoi/create") {

    }
    patch("hanoi/move") {
        moveSlice() runOn Base(call, environment)
    }
}
