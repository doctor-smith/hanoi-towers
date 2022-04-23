package hanoi.towers.component

import hanoi.towers.alg.apply
import hanoi.towers.alg.inverse
import androidx.compose.runtime.Composable
import hanoi.towers.data.Hanoi
import hanoi.towers.data.Moves
import hanoi.towers.data.reset
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.lens.Storage
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun ActionBar(
    indexOfCurrentMove: Storage<Int>,
    hanoi: Storage<Hanoi>,
    moves: Storage<Moves>,
    numberOfMoves: Storage<Int>,
    isPlaying: Storage<Boolean>,
    movesPerSecond: Storage<Int>
    ) {
    Button(
        attrs = {
            onClick {
                if(indexOfCurrentMove.read() > 0) {
                    indexOfCurrentMove.write( indexOfCurrentMove.read() - 1 )
                    hanoi.write( hanoi.read().apply(moves.read()[indexOfCurrentMove.read()].inverse()))
                }
            }
        }
    ) {
        Text("<")
    }
    Button(
        attrs = {
            onClick {
                if(indexOfCurrentMove.read() < numberOfMoves.read()) {
                    hanoi.write( hanoi.read().apply(moves.read()[indexOfCurrentMove.read()]) )
                    indexOfCurrentMove.write( indexOfCurrentMove.read() + 1 )
                }
            }
        }
    ) {
        Text(">")
    }
    Button(
        attrs = {
            onClick {
                isPlaying.write( !isPlaying.read() )
                CoroutineScope(Job()).launch{
                    while(isPlaying.read() && indexOfCurrentMove.read() < numberOfMoves.read()) {
                        promise {
                            delay((1_000.floorDiv(movesPerSecond.read())).toLong())
                            hanoi.write( hanoi.read().apply(moves.read()[indexOfCurrentMove.read()]) )
                            indexOfCurrentMove.write( indexOfCurrentMove.read() + 1 )
                        }.await()
                    }
                    isPlaying.write( false )
                }
            }
        }
    ) {
        Text(if(isPlaying.read()){"Pause"}else{"Play >"})
    }

    // TODO("Reset Button einbauen.")
    Button(attrs = {
        onClick {
            hanoi.write(hanoi.read().reset())
            indexOfCurrentMove.write(0)
        }
    }) {
        // TODO(I18n des Reset buttons)
        Text("Reset")
    }
}