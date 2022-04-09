package component

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.transparent
import org.jetbrains.compose.web.dom.Div

@Composable
@Suppress("FunctionName")
fun Slice(size: Int, maxWidth: Int = 100) {
    Div({style {
        display(DisplayStyle("flex"))
        height(20.px)
        maxWidth(maxWidth.px)
    }}) {
        Div({
            style {
                width(((10-size)*5).pc)
            }
        }) {  }
        Div({
            if(size > 0) {
                style {
                    width((size*10).pc)

                    border {
                        style = LineStyle("solid")
                        color = black
                        width = 1.px
                    }
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
        Div({
            style {
                width(((10-size)*5).pc)
            }
        }) {  }
    }
}
