package hanoi.towers.module.hanoi.action.db.move

import hanoi.towers.module.db.schema.Slice
import hanoi.towers.module.db.schema.Slices
import hanoi.towers.module.db.schema.Tower
import hanoi.towers.module.hanoi.data.api.Hanoi
import hanoi.towers.module.hanoi.data.api.Move
import hanoi.towers.module.hanoi.data.toApiType
import org.evoleq.math.x
import org.evoleq.util.DbAction
import org.evoleq.util.KlAction
import org.jetbrains.exposed.sql.transactions.transaction
import hanoi.towers.module.db.schema.Hanoi as HanoiEntity

val MoveSlice: KlAction<Move, Hanoi> = KlAction { move: Move ->
    DbAction { database ->
        transaction(database) {
            val targetTower = Tower.findById(move.targetTower)!!
            val slice = Slice.find { Slices.towerId eq move.sourceTower }.maxBy { slice: Slice -> slice.size }
            slice.tower = targetTower
            commit()
            HanoiEntity.findById(move.hanoiId)!!.toApiType()
        } x database
    }
}
