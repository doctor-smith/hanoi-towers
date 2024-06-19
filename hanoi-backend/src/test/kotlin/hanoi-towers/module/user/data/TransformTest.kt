
package hanoi.towers.module.user.data

import hanoi.towers.module.db.schema.Users
import hanoi.towers.module.db.schema.User
import hanoi.towers.module.db.schema.runSimpleH2Test

import junit.framework.TestCase.assertNotNull
import org.junit.Test

class TransformTest {
    @Test
    fun testTransformations() = runSimpleH2Test(Users) {
        User.new {
            username = "name"
            password = "pw"
        }
        testUserIdNotNull()
    }

    fun testUserIdNotNull() {
        val userEntity = User.find {
            Users.username eq "name"
        }.first()

        val user = userEntity.toApiType()
        assertNotNull(user.id)
        println("Transform Test passed")
    }
}