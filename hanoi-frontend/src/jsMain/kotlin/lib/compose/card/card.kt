package lib.compose.card

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun Card(content: @Composable ElementScope<HTMLElement>.() -> Unit) = Div({
    style {
        border {
            style(LineStyle.Solid)
            width(1.px)
            borderRadius(10.px)
            margin(10.px)
            padding(10.px)
        }
        width(20.pc)
        height(200.px)
    }
}) {
    content()
}
