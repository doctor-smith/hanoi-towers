package hanoi.towers.module.user.data

import kotlinx.serialization.Serializable
import org.evoleq.serializationx.UUIDSerializer
import java.util.*

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String,
    val password: String
)
