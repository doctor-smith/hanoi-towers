package hanoi.towers.page.testpage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import lib.compose.Markup
import lib.compose.storage.Store
import lib.optics.storage.Storage
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun TestStorageComponent() {
    Store({
        var x = remember { mutableStateOf<Int>(0) }
        Storage<Int>(
            read = {x.value},
            write = {newX -> x.value = newX}
        )
    }) {
        Div{Text(
            "${read()}"
        )}
        Button(
            attrs = {
                onClick {
                    write(read() + 1)
                }
            }
        ) {
            Text("+")
        }
        Button(
            attrs = {
                onClick {
                    write(read() - 1)
                }
            }
        ) {
            Text("-")
        }
    }


}
