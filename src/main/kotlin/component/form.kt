package component

import alg.moveTower
import androidx.compose.runtime.Composable
import data.Hanoi
import data.Moves
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.lens.Storage
import lib.maths.toThe
import org.jetbrains.compose.web.dom.NumberInput

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
    maxNumberOfSlices: Int
) {
    org.jetbrains.compose.web.dom.Form {
        Label("Turmhöhe")
        NumberInput(
            value = numberOfSlices.read(),
            min = 0,
            max = maxNumberOfSlices
        ) {
            onInput { event ->
                numberOfSlices.write(
                    with(event.value) {
                        when (this) {
                            null -> numberOfSlices.read()
                            else -> with(toInt()) {
                                if (this <= maxNumberOfSlices) {
                                    this
                                } else {
                                    error.write(
                                        "Mehr als $maxNumberOfSlices Scheiben sind nicht erlaubt (Dauert einfach zu lang!)"
                                    )
                                    numberOfSlices.read()
                                }
                            }
                        }
                    }
                )

                numberOfMoves.write((2 toThe numberOfSlices.read()) - 1)
                hanoi.write(Hanoi(
                    (1..numberOfSlices.read()).map { it }
                ))
                CoroutineScope(Job()).launch {
                    isComputingMoves.write(true)
                    promise {
                        moves.write(
                            moveTower(
                                hight = numberOfSlices.read(),
                                from = data.Tower.One,
                                to = data.Tower.Three,
                                stack = data.Tower.Two
                            )(Moves())
                        )
                    }.await()
                    indexOfCurrentMove.write(0)
                    isComputingMoves.write(false)
                }
            }
        }
    }
}

