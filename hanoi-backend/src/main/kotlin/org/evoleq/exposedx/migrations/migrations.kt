package org.evoleq.exposedx.migrations



import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

interface Migration {
    val id: Long
    val database: Database
    suspend fun Transaction.up(): Unit
    suspend fun Database.up(): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.Default, database) {
        // Make sure there is a table collecting performed migrations
        SchemaUtils.create(
            MigrationTable
        )
        // check if migration already exists, which means
        // that migration has already been performed
        val upToDate = MigrationEntry.find {
            (MigrationTable.migrationId eq this@Migration.id)
        }.firstOrNull().isNotNull()
        if(!upToDate) {
            println(
                "Running migration ${this@Migration.id}"
            )
            up()
            MigrationEntry.new {
                migrationId = this@Migration.id
            }
            true
        } else {
            println(
                "Skipping migration ${this@Migration.id}"
            )
            false
        }

    }

    suspend fun Database.down(): Unit
}

fun <T> T?.isNotNull(): Boolean = this != null

object MigrationTable : IntIdTable("migrations") {
    val migrationId: Column<Long> = MigrationTable.long("migration_id")
}
class MigrationEntry(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MigrationEntry>(
        MigrationTable
    )
    var migrationId by MigrationTable.migrationId
}


suspend fun <S, T> List<S>.mapSuspended(f: suspend (S)->T): List<T> {
    val list = arrayListOf<T>()
    forEach {
        list.add(f(it))
    }
    return list
}

suspend fun ArrayList<Database.()-> Migration>.runOn(database: Database): List<Boolean> =
    mapSuspended {
        it(database)
    }.sortedBy {
        it.id
    }.mapSuspended {
        with(it) {
            database.up().await()
        }
    }

