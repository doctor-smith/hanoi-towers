package hanoi.towers.application

import androidx.compose.runtime.*
import hanoi.towers.api.*
import hanoi.towers.component.loading.Loading
import hanoi.towers.data.*
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.compose.storage.Store
import org.jetbrains.compose.web.renderComposable


@Markup
@Suppress("FunctionName")
fun Application() = renderComposable(rootElementId = "root") {
    Store({ Storage() }) {
        when( langLoaded() ) {
            true -> UI(this)
            false -> Loading()
        }
    }
}