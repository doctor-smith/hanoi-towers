package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Markup
@Composable
@Suppress("FunctionName")
fun Loading() = Div({
    style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        // TODO("center vertically")
    }
}) {
    Div({
        style {
            border {
                width = 16.px
                style = LineStyle.Solid
                color = Color("#f3f3f3")
            }
            borderRadius(50.pc)
            //borderTop()
            width(120.px)
            height(120.px)
            animation("spin") {
                // TODO("Animate")
            }

        }
    }) {}
}