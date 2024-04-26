package hanoi.towers.module.db.schema

import org.jetbrains.exposed.dao.id.UUIDTable


object UserGames: UUIDTable() {
    val userId = reference("user_id", Users)
    val gameId = reference("game_id", Games)


    // TODO add unique constraint on (userId, gamId)
    // override val primaryKey = PrimaryKey(userId, gameId)
}