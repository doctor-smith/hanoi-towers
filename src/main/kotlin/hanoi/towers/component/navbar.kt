package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.language.Block
import lib.language.get
import lib.lens.Storage
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.Select
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun NavBar(
    locales: Storage<List<String>>,
    locale: Storage<String>,
    texts: Block
) = Div({
    style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.FlexEnd)
    }
}) {
    val currentLocale = locale.read()
    Select {
        locales.read().forEach { s ->
            Option(s, {
                if(s == currentLocale){
                    selected()
                }
                onClick {
                    locale.write(s)
                }
            }) {
                Text(texts[s])
            }
        }
    }
}