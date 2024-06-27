package hanoi.towers.component.loading

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Markup
@Composable
@Suppress("FunctionName")

fun Loading() = Div ({
    classes("loading-page")
})
