package hanoi.towers.module.hanoi.action.api.move

import hanoi.towers.module.hanoi.data.api.Hanoi
import io.ktor.server.response.*
import org.evoleq.math.x
import org.evoleq.util.ApiAction
import org.evoleq.util.KlAction

val Respond: KlAction<Hanoi, Unit> = { hanoi: Hanoi ->
    ApiAction { call ->
        call.respond(hanoi) x call
    }
}
