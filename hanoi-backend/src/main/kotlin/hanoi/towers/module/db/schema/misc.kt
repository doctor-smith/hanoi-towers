package hanoi.towers.module.db.schema

import org.jetbrains.exposed.dao.id.UUIDTable

object DualPlayerGames : UUIDTable() {
    val towerHeight = integer("tower_height")
    val playerOne = reference("user", Users)
    val playerTwo = reference("user", Users)

    val winner = reference("user", Users)
}

object Stats : UUIDTable()
