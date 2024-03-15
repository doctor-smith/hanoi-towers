package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import hanoi.towers.alg.moveTower
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.compose.error.OnError
import lib.compose.label.Label
import lib.language.Block
import lib.language.get
import lib.maths.toThe
import lib.optics.storage.Storage
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.NumberInput
import org.jetbrains.compose.web.events.SyntheticInputEvent
import org.w3c.dom.HTMLInputElement

@Markup
@Composable
@Suppress("FunctionName")
fun Form(
    mode: Mode,
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
                    when(mode) {
                        is Mode.Automatic -> handleEventOnSolver(
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
                        is Mode.Play -> handleEventWhenPlaying(
                            event,
                            numberOfSlices,
                            numberOfMoves,
                            hanoi,
                            error,
                            texts,
                            maxNumberOfSlices
                        )
                        is Mode.Cheat -> handleEventWhenCheat(
                            event,
                            numberOfSlices,
                            numberOfMoves,
                            hanoi,
                            error,
                            texts,
                            maxNumberOfSlices
                        )
                    }
                }
            }
        }
        OnError(error)
    }
}

suspend fun handleEventOnSolver(
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

suspend fun handleEventWhenPlaying(
    event: SyntheticInputEvent<Number?, HTMLInputElement>,
    numberOfSlices: Storage<Int>,
    numberOfMoves: Storage<Int>,
    hanoi: Storage<Hanoi>,
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
    }
}

suspend fun handleEventWhenCheat(
    event: SyntheticInputEvent<Number?, HTMLInputElement>,
    numberOfSlices: Storage<Int>,
    numberOfMoves: Storage<Int>,
    hanoi: Storage<Hanoi>,
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
    }
}




