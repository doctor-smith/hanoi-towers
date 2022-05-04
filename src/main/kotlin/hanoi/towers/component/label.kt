package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.attributes.forId
import org.jetbrains.compose.web.css.paddingRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun Label(text: String, width: Int = 100, id: String = "") {
    org.jetbrains.compose.web.dom.Label(attrs = {
        forId(id)
        style {
            paddingRight(10.px)
            width(width.px)
        }
    }) {
        Text(text)
    }
}