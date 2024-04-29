package hanoi.towers.module.hanoi.data.api

import kotlinx.serialization.Serializable
import org.evoleq.serializationx.UUIDSerializer
import java.util.*

@Serializable
data class Hanoi(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val towerOne: Tower,
    val towerTwo: Tower,
    val towerThree: Tower
)
@Serializable
data class Tower(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val slices: List<Slice>
)
@Serializable
data class Slice(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val size: Int
)
