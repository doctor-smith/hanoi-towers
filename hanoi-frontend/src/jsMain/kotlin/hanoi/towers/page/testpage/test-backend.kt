package hanoi.towers.page.testpage

import androidx.compose.runtime.*
import hanoi.towers.data.environment.Environment
import hanoi.towers.data.environment.getEnv
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lib.compose.Markup
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun TestBackend()= Div {
    var response: String by remember { mutableStateOf("") }
    var count: Int by remember{ mutableStateOf(0) }
    val environment: Environment = getEnv()
    Button(
        attrs = {
            onClick {
                CoroutineScope(Job()).launch {
                    response = with(HttpClient(Js)) {
                        count++
                        get<String>("${environment.hanoiBackendURL}/hello") {
                            port = environment.hanoiBackendPort
                        }
                    }
                }
            }
        }
    ) {
        Text("Hello Backend")
    }
    Text("Response no $count:")
    Text(response)
}