package hanoi.towers.module.hanoi.action

import hanoi.towers.module.hanoi.action.api.move.ReceiveMove
import hanoi.towers.module.hanoi.action.api.move.Respond
import hanoi.towers.module.hanoi.action.db.move.MoveSlice
import org.evoleq.math.state.times

suspend fun moveSlice() = ReceiveMove * MoveSlice * Respond
