package `hanoi-towers`
import org.junit.Test
import kotlin.test.fail

class FailingTest {

    @Test
    fun testThatFails() {
        // Dieser Test wird absichtlich fehlschlagen
        fail("This test is designed to fail.")
    }
}
