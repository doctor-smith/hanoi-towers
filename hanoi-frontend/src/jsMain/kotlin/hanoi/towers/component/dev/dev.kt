package hanoi.towers.component.dev

import androidx.compose.runtime.Composable
import hanoi.towers.data.environment.Environment
import lib.compose.DevMarkup
import lib.optics.storage.Storage
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

@DevMarkup
@Composable
@Suppress("FunctionName")
fun onDev(storage: Storage<Environment>, environments: Set<String> = setOf("DEV", "TEST"), content: @Composable ElementScope<HTMLElement>.() -> Unit) {
    if (storage.read().environment in environments) {
        Div { content() }
    }
}
