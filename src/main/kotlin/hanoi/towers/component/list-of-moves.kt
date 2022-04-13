package hanoi.towers.component

import androidx.compose.runtime.Composable
import hanoi.towers.data.Moves
import lib.compose.Markup
import lib.lens.Storage
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun ListOfMoves(
    moves: Storage<Moves>,
    isComputingMoves: Storage<Boolean>

)  = Div {
    H3 { Text("Spielzüge") }
    Div({
        style {
            overflowY("auto")
            maxHeight(500.px)
            width(20.pc)
        }
    }) {
        if (isComputingMoves.read()) {
            Text("Spielzüge werden berechnet ...")
        } else {
            with(moves.read()) {
                if(isEmpty()) {
                    Text("Bitte wähle eine Turmhöhe > 0")
                } else {
                    // TODO("Finde andere Darstellung, etwa durch Tabelle oder FlexBoxes")
                    Ul {
                        forEachIndexed { index, it ->
                            Li({
                                style {
                                    listStyleType("none")
                                }
                            }) {
                                Text("$it // ${index + 1}")
                            }
                        }
                    }
                }
            }

        }
    }
}