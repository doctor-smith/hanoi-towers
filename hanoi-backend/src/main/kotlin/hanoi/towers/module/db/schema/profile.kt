package hanoi.towers.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Profiles : UUIDTable() {
    val name = varchar("name", 50)
}

class Profile(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Profile>(Profiles)

    var name by Profiles.name
    // val users by User referrersOn Users.profile
}
