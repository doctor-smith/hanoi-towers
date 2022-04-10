package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun Flex(content: @Composable ElementScope<HTMLElement>.()->Unit) {
    Div({
        style {
            display(DisplayStyle("flex"))
        }
    }) {
        content()
    }
}