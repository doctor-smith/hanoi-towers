package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.lens.Storage
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun OnError(message: String?, onHide: ()->Unit) {
    if(message != null) {
        Div(attrs = {
            style {
                height(30.px)
                borderRadius(3.px)
                border{
                    color = Color.red
                    width = 1.px
                    style = LineStyle("solid")
                }
            }
            onClick { onHide() }
        }) {
            Text(message)

        }
    }
}


@Markup
@Composable
@Suppress("FunctionName")
fun OnError(error: Storage<String?>) {
    val message = error.read()
    if(message != null) {
        Div(attrs = {
            style {
                height(30.px)
                borderRadius(3.px)
                border{
                    color = Color.red
                    width = 1.px
                    style = LineStyle("solid")
                }
            }
            onClick { error.write(null) }
        }) {
            Text(message)

        }
    }
}