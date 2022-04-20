package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.compose.Modal
import lib.lens.Storage
import lib.lens.remove
import lib.maths.x
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Suppress("FunctionName")
fun CookieDisclaimer(
    modals: Storage<Map<Int,@Composable ElementScope<HTMLElement>.() -> Unit>>,
    isCookieDisclaimerConfirmed: Storage<Boolean>
) = 1 x Modal(
    1,
    {
        isCookieDisclaimerConfirmed.write(true)
        modals.remove(1)

    }
) {
    Div {
        Text("hello modal")
    }
}