package hanoi.towers.component.hanoi

import hanoi.towers.alg.moveTower
import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.compose.error.OnError
import lib.compose.label.Label
import lib.language.Block
import lib.language.get
import lib.optics.storage.Storage
import lib.maths.toThe
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.NumberInput
import org.jetbrains.compose.web.events.SyntheticInputEvent
import org.w3c.dom.HTMLInputElement

@Markup
@Composable
@Suppress("FunctionName")
fun Form(
    numberOfSlices: Storage<Int>,
    numberOfMoves: Storage<Int>,
    hanoi: Storage<Hanoi>,
    moves: Storage<Moves>,
    isComputingMoves: Storage<Boolean>,
    indexOfCurrentMove: Storage<Int>,
    error: Storage<String?>,
    texts: Block,
    maxNumberOfSlices: Int
) {


    Div {
        Label(texts["towerHeight"], id="tower-height")
        NumberInput(
            value = numberOfSlices.read(),
            min = 0,
            max = maxNumberOfSlices,
        ) {
            id("tower-height")
            onInput { event ->
                CoroutineScope(Job()).launch{
                    handleEvent(
                        event,
                        numberOfSlices,
                        numberOfMoves,
                        hanoi,
                        moves,
                        isComputingMoves,
                        indexOfCurrentMove,
                        error,
                        texts,
                        maxNumberOfSlices
                    )
                }
            }
        }
        OnError(error)
    }
}

suspend fun handleEvent(
    event: SyntheticInputEvent<Number?, HTMLInputElement>,
    numberOfSlices: Storage<Int>,
    numberOfMoves: Storage<Int>,
    hanoi: Storage<Hanoi>,
    moves: Storage<Moves>,
    isComputingMoves: Storage<Boolean>,
    indexOfCurrentMove: Storage<Int>,
    error: Storage<String?>,
    texts: Block,
    maxNumberOfSlices: Int
) = coroutineScope {
        promise {
            numberOfSlices.write(
                with(event.value) {
                    when (this) {
                        null -> numberOfSlices.read()
                        else -> with(toInt()) {
                            if (this <= maxNumberOfSlices) {
                                this
                            } else {
                                error.write(
                                    texts["error.limitedNumberOfSlices"]
                                        .replace(
                                            "__MAX_NUMBER_OF_SLICES__",
                                            "$maxNumberOfSlices"
                                        )
                                )
                                numberOfSlices.read()
                            }
                        }
                    }
                }
            )
        }.then {
            numberOfMoves.write((2 toThe numberOfSlices.read()) - 1)
        }.then {
            hanoi.write(Hanoi(
                (1..numberOfSlices.read()).map { it }
            ))
        }.then {
            isComputingMoves.write(true)
        }.then {
            moves.write(
                moveTower(
                    hight = numberOfSlices.read(),
                    from = Tower.One,
                    to = Tower.Three,
                    stack = Tower.Two
                )(Moves())
            )
        }.then {
            indexOfCurrentMove.write(0)
        }.then {
            isComputingMoves.write(false)
        }
    }



