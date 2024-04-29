package lib.compose.structure

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun Block(name: String, content: @Composable ElementScope<HTMLElement>.() -> Unit) {
    Div {
        content()
    }
}
