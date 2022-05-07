package lib.compose.modal

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.language.Block
import lib.language.get
import lib.optics.lens.Storage
import lib.optics.lens.remove
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement

typealias Modals<Id> = Map<Id, @Composable ElementScope<HTMLElement>.() -> Unit>

@Markup
@Composable
@Suppress("FunctionName")
fun <Id> ModalLayer(
    zIndex: Int = 1000,
    modals: Storage<Modals<Id>>,
    bottomUp: Boolean = false,
    content: @Composable ElementScope<HTMLElement>.()->Unit
) {
    if(modals.read().keys.isNotEmpty()) {
        Div({
            style {
                property("z-index", zIndex)
                position(Position.Absolute)
                width(100.vw)
                boxSizing("border-box")
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                height(100.vh)
                backgroundColor(Color.black)
                opacity(0.5)
                if(bottomUp) {
                    justifyContent(JustifyContent.FlexEnd)
                }
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
@Suppress("FunctionName")
fun <Id> Modal(
    id: Id,
    modals: Storage<Modals<Id>>,
    onOk: ()->Unit,
    onCancel: (()->Unit)?,
    texts: Block,
    content: @Composable ElementScope<HTMLElement>.()->Unit
):  @Composable ElementScope<HTMLElement>.()->Unit = {

    val close: Id.()-> Unit = { modals.remove( this )}

    Div({
        style {
            // minHeight("300px")
            border {
                style = LineStyle.Solid
                color = Color("black")
                width = 1.px
            }
            borderRadius(10.px)
            backgroundColor(Color.white)
            width(90.percent)
            marginLeft(5.percent)
        }
    }) {
        //
        // Header
        //
        if(onCancel != null) {
            Div({
                style {
                    display(DisplayStyle.Flex)
                    justifyContent(JustifyContent.FlexEnd)
                }
            }) {
                Button({
                    style { classes("button") }
                    onClick { id.close() }
                }) {
                    // Text("x")

                    I({
                        style { classes("fa-solid", "fa-xmark") }
                    })
                }
            }
        }

        H3({
            style {
                marginLeft(10.px)
            }
        }){
            Text(texts["title"])
        }

        //
        // Content area
        //
        Div({
            style {
                maxWidth(80.pc)
                marginLeft(10.px)
                marginBottom(10.px)
            }
        }) {
            content()
        }

        //
        // Footer
        //
        Div({
            style {
                height(30.px)
                marginBottom(0.px)
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.FlexEnd)
            }
        }) {
            if(onCancel != null) {
                Button({
                    onClick {
                        onCancel()
                        id.close()
                    }
                }) {
                    Text(texts["cancelButton.title"])
                }
            }
            Button({
                onClick {
                    onOk()
                    id.close()
                }
            }) {
                Text(texts["okButton.title"])
            }
        }
    }
}