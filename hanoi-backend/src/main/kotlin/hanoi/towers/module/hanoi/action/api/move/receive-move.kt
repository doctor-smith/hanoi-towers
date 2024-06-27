package hanoi.towers.module.hanoi.action.api.move

import hanoi.towers.module.hanoi.data.api.Move
import io.ktor.server.request.*
import org.evoleq.math.x
import org.evoleq.util.Action
import org.evoleq.util.ApiAction

val ReceiveMove: Action<Move> = ApiAction {
    call -> call.receive<Move>() x call
}
