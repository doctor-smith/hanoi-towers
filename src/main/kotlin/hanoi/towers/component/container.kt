package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.pc
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun Container(content: @Composable ElementScope<HTMLElement>.()->Unit) {
    Div({
        style {
            width(80.pc)
            marginLeft(20.pc)
            marginRight(20.pc)
        }
    }) {
        content()
    }
}