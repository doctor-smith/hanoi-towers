package hanoi.towers.module.db.schema


import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class UserTest {
    @Test
    fun readAndWriteUser() = runSimpleH2Test(Users){
        User.new {
            username = "name"
            password = "pw"
        }

        val  user = User.find {
            Users.username eq "name"
        }.firstOrNull()

        assertNotNull(user)

        val newUsername = "newName"

        User.findByIdAndUpdate(user.id.value) {
            it.username = newUsername
        }

        val  user1 = User.find {
            Users.id eq user.id
        }.first()

        assertEquals(newUsername, user1.username)


    }

}
