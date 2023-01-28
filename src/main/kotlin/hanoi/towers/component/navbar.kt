package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.compose.routing.navigate
import lib.language.Block
import lib.language.get
import lib.optics.storage.Storage
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun NavBar(
    locales: Storage<List<String>>,
    locale: Storage<String>,
    localeTexts: Block,
    navTexts: Block
    ) = Div({
    style {
        paddingTop(10.px)
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.FlexEnd)
    }
}) {
    Button({onClick {
        navigate("/")
    }}) { Text("Home") }
    Button({onClick {
        navigate("game")
    }}) { Text("Game") }
    Button({onClick {
        navigate("cheat")
    }}) { Text("Cheat") }
    Button({onClick {
        navigate("solver")
    }}) { Text("Solver") }
    Div({style { width(50.px) }}) {  }

    val currentLocale = locale.read()
    Div({classes("select")}) {
        Select {
            locales.read().forEach { s ->
                Option(s, {
                    if (s == currentLocale) {
                        selected()
                    }
                    onClick {
                        locale.write(s)
                    }
                }) {
                    Text(localeTexts[s])
                }
            }
        }
    }
}