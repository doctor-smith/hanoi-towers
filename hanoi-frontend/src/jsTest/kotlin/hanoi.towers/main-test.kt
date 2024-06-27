@file:Suppress("MatchingDeclarationName", "UnusedPrivateProperty")

package hanoi.towers

import hanoi.towers.application.Application
import io.ktor.http.*
import kotlin.test.assertTrue
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest

class MainTest {

     @OptIn(ComposeWebExperimentalTestsApi::class)

     // @Test
     fun exampleUiTest() {
          Application()

          val testNode = runTest { this.root.children.item(0) }

          val textNode = testNode as? ContentType.Text ?: error("Text node not found")

          assertTrue { true }
     }
}
