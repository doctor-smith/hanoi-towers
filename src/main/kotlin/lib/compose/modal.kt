package lib.compose

import androidx.compose.runtime.Composable
import lib.lens.Storage
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun <Id> ModalLayer(
    zIndex: Int = 1000,
    modals: Storage<Map<Id, @Composable ElementScope<HTMLElement>.() -> Unit>>,
    content: @Composable ElementScope<HTMLElement>.()->Unit
) {

    //console.log("rendering modal-layer")

    if(modals.read().keys.isNotEmpty()) {
        Div({
            style {
                property("z-index", zIndex)
                position(Position.Absolute)
                marginLeft(20.pc)
                marginTop(10.pc)
                width(80.pc)
                height(80.px)
                border {

                }
                backgroundColor(Color("red"))
            }
        }){
            modals.read().values.forEach {
                it()
            }
        }
    }
    Div {
        content()
    }
}

@Markup
// @Composable
@Suppress("FunctionName")
fun <Id> Modal(id: Id, close: (Id)-> Unit, content: @Composable ElementScope<HTMLElement>.()->Unit):  @Composable ElementScope<HTMLElement>.()->Unit = {
    Div {
        content()

        Button({
            onClick {
                close(id)
            }
        }) { Text("Ok") }
    }
}