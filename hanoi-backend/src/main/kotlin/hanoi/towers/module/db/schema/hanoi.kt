package hanoi.towers.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Hanois : UUIDTable() {
    val towerHeight = integer("height")
    val startTower = integer("start_tower").default(1)
    val targetTower = integer("target_tower").default(3)
    val towerOneId = reference("tower_one_id", Towers)
    val towerTwoId = reference("tower_two_id", Towers)
    val towerThreeId = reference("tower_three_id", Towers)
}

class Hanoi(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<Hanoi>(Hanois)

    var towerHeight by Hanois.towerHeight
    var startTower by Hanois.startTower
    var targetTower by Hanois.targetTower
    var towerOne by Tower referencedOn Towers.hanoiId
    var towerTwo by Tower referencedOn Towers.hanoiId
    var towerThree by Tower referencedOn Towers.hanoiId
}

object Towers : UUIDTable() {
    val hanoiId = reference("hanoi_id", Hanois)
}


class Tower(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<Tower>(Towers)

    val slices by Slice referrersOn Slices.towerId
}

object Slices : UUIDTable()  {
    val towerId = reference("tower_id", Towers)
    val size = integer("size")
}

class Slice(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<Slice>(Slices)

    var tower by Tower referencedOn Slices.towerId
    var size by Slices.size
}