package lib.language.compose

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.language.Block
import lib.language.get
import org.jetbrains.compose.web.dom.Text as CText

@Markup
@Composable
@Suppress("FunctionName")
fun Block.Text(path: String) = CText(this[path])