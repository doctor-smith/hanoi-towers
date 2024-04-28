package hanoi.towers.module.db.schema

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column

object GameTypes : UUIDTable() {
    val name: Column<String> = varchar("name", 50).uniqueIndex()
    val numberOfPlayers: Column<Int> = integer("number_of_players")
}
