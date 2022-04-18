package hanoi.towers.component

import hanoi.towers.alg.moveTower
import androidx.compose.runtime.Composable
import hanoi.towers.data.Hanoi
import hanoi.towers.data.*
import hanoi.towers.data.Moves
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.language.Block
import lib.language.Lang
import lib.language.get
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
    texts: Block,
    maxNumberOfSlices: Int
) {
    org.jetbrains.compose.web.dom.Form {
        Label(texts["hanoi.mainPage.form.towerHeight"])
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
                                        texts["hanoi.mainPage.error.limitedNumberOfSlices"]
                                            .replace("__MAX_NUMBER_OF_SLICES__", "$maxNumberOfSlices")
                                        //"Mehr als $maxNumberOfSlices Scheiben sind nicht erlaubt (Dauert einfach zu lang!)"
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
                                from = Tower.One,
                                to = Tower.Three,
                                stack = Tower.Two
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

