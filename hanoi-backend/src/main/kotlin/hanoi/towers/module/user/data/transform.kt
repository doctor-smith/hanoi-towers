package hanoi.towers.module.user.data
import hanoi.towers.module.db.schema.User as UserEntity

fun UserEntity.toApiType(): User = User(
    id.value,
    username, password
)
