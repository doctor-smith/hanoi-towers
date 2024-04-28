package hanoi.towers.module.hanoi.data.api

import kotlinx.serialization.Serializable
import org.evoleq.serializationx.UUIDSerializer
import java.util.UUID

@Serializable
data class Move(
    @Serializable(with = UUIDSerializer::class)
    val hanoiId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val sourceTower: UUID,
    @Serializable(with = UUIDSerializer::class)
    val targetTower: UUID
)
