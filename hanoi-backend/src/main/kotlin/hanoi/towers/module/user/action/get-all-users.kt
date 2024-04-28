package hanoi.towers.module.user.action

import hanoi.towers.module.user.data.User
import io.ktor.server.response.*
import org.evoleq.math.state.bind
import org.evoleq.math.state.map
import org.evoleq.math.x
import org.evoleq.util.ApiAction
import org.evoleq.util.DbAction
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import hanoi.towers.module.db.schema.User as UserEntity

/**
 * Get all users in the database
 */
suspend fun getAllUsers() = DbAction {
    database ->
    transaction(database) {
        UserEntity.all()
    } x database
} map { userEntities: SizedIterable<UserEntity> ->
    userEntities.map { userEntity ->
        User(
            userEntity.id.value,
            userEntity.username,
            userEntity.password
        )
    }
} bind { users ->
    ApiAction { call ->
        call.respond(users) x call
    }
}
