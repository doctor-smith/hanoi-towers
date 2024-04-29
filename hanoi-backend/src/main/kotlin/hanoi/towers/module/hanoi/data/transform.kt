package hanoi.towers.module.hanoi.data

import hanoi.towers.module.hanoi.data.api.Hanoi
import hanoi.towers.module.hanoi.data.api.Slice
import hanoi.towers.module.hanoi.data.api.Tower
import hanoi.towers.module.db.schema.Hanoi as HanoiEntity
import hanoi.towers.module.db.schema.Slice as SliceEntity
import hanoi.towers.module.db.schema.Tower as TowerEntity

fun SliceEntity.toApiType() = Slice(
    id = id.value,
    size = size
)

fun TowerEntity.toApiType() = Tower(
    id = id.value,
    slices = slices.map { it.toApiType() }
)

fun HanoiEntity.toApiType() = Hanoi(
    id = id.value,
    towerOne = towerOne.toApiType(),
    towerTwo = towerTwo.toApiType(),
    towerThree = towerThree.toApiType()
)
