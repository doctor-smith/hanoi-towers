package lib.compose

import androidx.compose.runtime.Composable
import lib.language.Block
import lib.language.get
import lib.lens.Storage
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun <Id> ModalLayer(
    zIndex: Int = 1000,
    modals: Storage<Map<Id, @Composable ElementScope<HTMLElement>.() -> Unit>>,
    content: @Composable ElementScope<HTMLElement>.()->Unit
) {
    if(modals.read().keys.isNotEmpty()) {
        Div({
            style {
                property("z-index", zIndex)
                position(Position.Absolute)
                marginLeft(20.pc)
                marginTop(10.pc)
                width(80.pc)
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
    texts: Block,
    onOk: ()->Unit,
    onCancel: (()->Unit)?,
    close: ()-> Unit,
    content: @Composable ElementScope<HTMLElement>.()->Unit
):  @Composable ElementScope<HTMLElement>.()->Unit = {
    Div({
        style {
            // minHeight("300px")
            border {
                style = LineStyle.Solid
                color = Color("black")
                width = 1.px
            }
            borderRadius(10.px)
            backgroundColor(Color("white"))
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
                    onClick { close() }
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
                        close()
                    }
                }) {
                    Text(texts["cancelButton.title"])
                }
            }
            Button({
                onClick {
                    onOk()
                    close()
                }
            }) {
                Text(texts["okButton.title"])
            }
        }
    }
}