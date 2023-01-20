package hanoi.towers.application

import androidx.compose.runtime.*
import hanoi.towers.api.*
import hanoi.towers.component.Loading
import hanoi.towers.component.UI
import hanoi.towers.data.*
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.compose.storage.Store
import org.jetbrains.compose.web.renderComposable


@Markup
@Suppress("FunctionName")
fun Application() = renderComposable(rootElementId = "root") {
    Store({ Storage() }) {
        if ( langLoaded() ) {
            UI(this)
        } else {
            Loading()
        }
    }
}


