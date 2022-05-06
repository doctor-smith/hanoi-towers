package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.transparent
import org.jetbrains.compose.web.dom.Div

@Markup
@Composable
@Suppress("FunctionName")
fun Slice(size: Int, maxWidth: Int = 100) {
    Div({style {
        display(DisplayStyle("flex"))
        height(20.px)
        maxWidth(maxWidth.px)
    }}) {
        Space(size)
        Box(size)
        Space(size)
    }
}

@Markup
@Composable
@Suppress("FunctionName")
private fun Space(size: Int) =  Div({
    style {
        width(((10-size)*5).pc)
    }
}) {  }

@Markup
@Composable
@Suppress("FunctionName")
private fun Box(size:Int) = Div({
    if(size > 0) {
        style {
            width((size*10).pc)
            border {
                style = LineStyle.Solid
                color = black
                width = 1.px
            }
            boxSizing("border-box")
        }
    } else {
        style {
            width(100.pc)
            border {
                color = transparent
            }
        }
    }
}) {}