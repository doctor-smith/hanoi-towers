package hanoi.towers.component.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import hanoi.towers.data.i18n.language
import hanoi.towers.data.i18n.locale
import hanoi.towers.data.i18n.locales
import hanoi.towers.data.navigation.NavBar
import hanoi.towers.data.navigation.i18n
import kotlinx.coroutines.launch
import lib.compose.Markup
import lib.compose.routing.navigate
import lib.language.Block
import lib.language.component
import lib.language.get
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun NavBar(
    navBar: Storage<NavBar>
) = Div({
    style {
        paddingTop(10.px)
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.FlexEnd)
    }
}) {

    val i18n = navBar * i18n
    val currentLocale = (i18n * locale).read()
    val scope = rememberCoroutineScope()

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

    Div({classes("select")}) {
        Select {
            (i18n * locales).read().forEach { s ->
                Option(s, {
                    if (s == currentLocale) {
                        selected()
                    }
                    onClick {
                        scope.launch {
                            (i18n * locale).write(s)
                        }
                    }
                }) {
                    Text(((i18n * language).read() as Block).component("hanoi.locales")[s])
                }
            }
        }
    }
}