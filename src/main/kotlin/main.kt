import alg.*
import androidx.compose.runtime.*
import component.Container
import component.OnError
import component.Hanoi
import component.Label
import data.Hanoi as Towers
import data.Moves
import data.Tower
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.maths.toThe
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

const val maxNumberOfSlices = 10

@Markup
@Composable
@Suppress("FunctionName")
fun Body() {

    var numberOfSlices by remember{ mutableStateOf(0)}
    var moves by remember{ mutableStateOf( Moves() ) }
    var hanoi by remember { mutableStateOf(Towers()) }
    var indexOfCurrentMove by remember { mutableStateOf(0)}
    var numberOfMoves by remember { mutableStateOf(0) }
    var isComputingMoves by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var movesPerSecond by remember { mutableStateOf(4) }
    var error by remember { mutableStateOf<String?>(null) }

    Container{
        H1 { Text("Die Türme von Hanoi") }

        Form{
            Label("Turmhöhe")
            NumberInput(
                value = numberOfSlices,
                min = 0,
                max = maxNumberOfSlices
            ){
                onInput { event ->
                    numberOfSlices = with(event.value) {
                        when(this){
                            null -> numberOfSlices
                            else -> with(toInt()) {
                                if(this <= maxNumberOfSlices) {
                                    this
                                } else {
                                    error = "Mehr als $maxNumberOfSlices Scheiben sind nicht erlaubt (Dauert einfach zu lang!)"
                                    numberOfSlices
                                }
                            }
                        }
                    }

                    numberOfMoves = (2 toThe numberOfSlices) - 1
                    hanoi = Towers(
                        (1..numberOfSlices).map{ it }
                    )
                    CoroutineScope(Job()).launch{
                        isComputingMoves = true
                        promise {
                            moves = moveTower(
                                hight = numberOfSlices,
                                from = Tower.One,
                                to = Tower.Three,
                                stack = Tower.Two
                            )(Moves())
                        }.await()
                        indexOfCurrentMove = 0
                        isComputingMoves = false
                    }
                }
            }
        }
        OnError(error) { error = null }

        H3{ Text("Anzahl der nötigen Züge: 2^$numberOfSlices -1 = $numberOfMoves") }

        H3 { Text("Liste der Spielzüge bei Turmhöhe $numberOfSlices") }

        Button(
            attrs = {
                onClick {
                    if(indexOfCurrentMove > 0) {
                        indexOfCurrentMove--
                        hanoi = hanoi.apply(moves[indexOfCurrentMove].inverse())
                    }
                }
            }
        ) {
            Text("<")
        }
        Button(
            attrs = {
                onClick {
                    if(indexOfCurrentMove < numberOfMoves) {
                        hanoi = hanoi.apply(moves[indexOfCurrentMove])
                        indexOfCurrentMove++
                    }
                }
            }
        ) {
            Text(">")
        }

        Button(
            attrs = {
                onClick {
                    isPlaying = !isPlaying
                    GlobalScope.launch{
                        while(isPlaying && indexOfCurrentMove < numberOfMoves) {
                            promise {
                                delay((1_000.floorDiv(movesPerSecond)).toLong())
                                hanoi = hanoi.apply(moves[indexOfCurrentMove])
                                indexOfCurrentMove++
                            }.await()
                        }
                        isPlaying = false
                    }
                }
            }
        ) {
            Text(if(isPlaying){"Pause"}else{"Play >"})
        }

        Div({
            style{display(DisplayStyle("flex"))}
        }) {
            Div({
                style {
                    overflowY("auto")
                    maxHeight(500.px)
                    width(20.pc)
                }
            }) {
                if(isComputingMoves) {
                    Text("Spielzüge werden berechnet ...")
                }
                else {
                    Ul {
                        moves.forEachIndexed { index, it ->
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
            Div({
                style {
                    width(75.pc)
                    paddingLeft(10.px)
                }
            }){
                Hanoi(
                    moves,
                    hanoi.one,
                    hanoi.two,
                    hanoi.three,
                    capacity = maxNumberOfSlices
                )
            }
        }
    }
}