package hanoi.towers.page.mainpage

import androidx.compose.runtime.Composable
import hanoi.towers.component.layout.Flex
import hanoi.towers.data.*
import hanoi.towers.data.pages.main.Main
import hanoi.towers.data.pages.main.texts
import lib.compose.Markup
import lib.compose.card.Card
import lib.compose.routing.navigate
import lib.language.Lang
import lib.language.get
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.*

const val loremIpsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, ..."
@Markup
@Composable
@Suppress("FunctionName")
fun MainPage(storage: Storage<Main>) {

    val texts: Lang = (storage * texts).read()


    Article({
        // style { width(80.pc) }
    }) {
        H1 { Text(texts["headline"]) }

        P { Text(texts["introduction"]) }

        H2 { Text(texts["rulesOfTheGame.headline"]) }

        Ul {
            Li { P { Text(texts["rulesOfTheGame.rule1"]) } }
            Li { P { Text(texts["rulesOfTheGame.rule2"]) } }
            Li { P { Text(texts["rulesOfTheGame.rule3"]) } }
        }

        H2 { Text(texts["gameModes.headline"]) }

        Flex {
            Card {
                Div({
                    onClick { navigate("game") }
                }) {
                    H3 { Text(texts["gameModes.game.headline"]) }
                    Text(texts["gameModes.game.description"])
                }
            }

            Card {
                Div({
                    onClick { navigate("cheat") }
                }) {
                    H3 { Text(texts["gameModes.cheat.headline"]) }
                    Text(texts["gameModes.cheat.description"])
                }
            }

            Card() {
                Div({
                    onClick { navigate("solver") }
                }) {
                    H3 { Text(texts["gameModes.solver.headline"]) }
                    Text(texts["gameModes.solver.description"])
                }
            }
        }
    }
}