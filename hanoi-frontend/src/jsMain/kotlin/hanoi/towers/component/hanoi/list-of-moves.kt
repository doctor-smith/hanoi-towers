package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Moves
import lib.compose.Markup
import lib.language.Block
import lib.language.get
import lib.optics.storage.Storage
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun ListOfMoves(
    moves: Storage<Moves>,
    isComputingMoves: Storage<Boolean>,
    texts: Block

)  = Div({
    style {
       // width(100.percent)
    }
}) {
    H3 { Text(texts["headline"]) }
    Div({
        style {
            overflowY("auto")
            maxHeight(500.px)
            minWidth(30.pc)
            flex(1)
        }
    }) {
        if (isComputingMoves.read()) {
            Text(texts["computingMovesMessage"])
        } else {
            with(moves.read()) {
                if(isEmpty()) {
                    Text(texts["hint"])
                } else {
                    Ul {
                        forEachIndexed { index, value ->
                            Li({
                                style {
                                    listStyleType("none")
                                }
                            }) {
                                Text("$value // ${index + 1}")
                            }
                        }                        
                    }
                }
            }

        }
    }
}
