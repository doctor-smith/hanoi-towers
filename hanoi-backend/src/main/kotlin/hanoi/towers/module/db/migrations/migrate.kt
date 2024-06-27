package hanoi.towers.module.db.migrations

import kotlinx.coroutines.runBlocking
import org.evoleq.exposedx.migrations.runOn
import org.jetbrains.exposed.sql.Database

interface DbConf {
    val url: String
    val driver: String
    val user: String
    val password: String
}
object Config {
    object H2 : DbConf{
        override val url: String = "jdbc:h2:mem:test"
        override val driver: String = "org.h2.Driver"
        override val user: String = "root"
        override val password: String = ""
    }

    object Local : DbConf {
        override val url: String = "jdbc:mysql://localhost:3306/hanoi_towers?useSSL=false&allowPublicKeyRetrieval=true"
        override val driver: String = "com.mysql.cj.jdbc.Driver"
        override val user: String = "root"
        override val password: String = "pass123"
    }

    object DB : DbConf {
        override val url: String = "jdbc:mysql://hanoi_database:3306/hanoi_towers?" +
            "useSSL=false&allowPublicKeyRetrieval=true"
        override val driver: String = "com.mysql.cj.jdbc.Driver"
        override val user: String = "root"
        override val password: String = "pass123"
    }
}
fun main(args: Array<String>?): Unit = runBlocking {
    val dbConf: DbConf = when {
        args.isNullOrEmpty() -> Config.Local
        else -> when (args[0]) {
            "H2" -> Config.H2
            "DB" -> Config.DB
            "Local" -> Config.Local
            else -> Config.Local
        }
    }
    migrate(dbConf)
}

suspend fun migrate(dbConf: DbConf) = with(dbConf) {
    println(
        """Running migrations on 
            |    url = ${dbConf.url} with 
            |    driver = ${dbConf.driver}
            |...    
            |""".trimMargin()
    )
    dbMigrations.runOn(
        Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
    )
    println("done")
}
