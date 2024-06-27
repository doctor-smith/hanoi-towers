package hanoi.towers.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.*


object Games: UUIDTable() {
    val type: Column<EntityID<UUID>> = reference("game_type_id", GameTypes)

}


class Game(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<Game>(Games)

    var type by Games.type
    val players by User via UserGames
}
