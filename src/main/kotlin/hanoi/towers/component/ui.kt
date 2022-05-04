package hanoi.towers.component

import androidx.compose.runtime.Composable
import hanoi.towers.data.*
import lib.compose.Markup
import lib.lens.Storage
import lib.lens.times
import hanoi.towers.page.mainpage.MainPage
import kotlinx.browser.document
import lib.compose.ModalLayer
import lib.compose.routing.Routing
import lib.compose.routing.navigate
import lib.language.*
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun UI(storage: Storage<AppData>) {

    val texts = (storage * languageLens).read() as Block
    val mainPageTexts = texts.component("hanoi.mainPage")

    document.title = mainPageTexts["title"]

    ModalLayer(
        1000,
        storage * modalsLens,
    true
    ) {
        CookieDisclaimer(
            texts.component("hanoi.cookieDisclaimer"),
            storage * modalsLens,
            storage * isCookieDisclaimerConfirmedLens
        )

        Container {
            NavBar(
                storage * localesLens,
                storage * localeLens,
                texts.component("hanoi.locales")
            )
            Routing("/") {
                component {
                    MainPage(storage, mainPageTexts)
                }
            }
        }
    }
}