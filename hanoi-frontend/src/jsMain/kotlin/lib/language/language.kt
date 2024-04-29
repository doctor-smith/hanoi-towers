package lib.language

interface Language {
    val locale: String
}

sealed class Lang(open val key: String) {
    data class Variable(
        override val key: String,
        val value: String
    ) : Lang(key)
    data class Block(
        override val key: String,
        val value: List<Lang>
    ) : Lang(key)
}

fun Lang?.find(name: String): Lang? = when (this) {
    null -> null
    is Lang.Variable -> this
    is Lang.Block -> value.find { this.key == name }
}

@I18N
operator fun Lang.get(path: String): String = with(Segment().run(path)) {
    when (this@get) {
        is Var -> this@get.value
        is Block -> with(this@get.value.find { it.key == result!! }) {
            when (this) {
                null -> throw Exception("There is no Element in block '${this@get.key}' with key = '$result'")
                else -> this[rest]
            }
        }
    }
}

@I18N
infix fun String.ofComponent(component: Block): Block = component.component(this)

@I18N
infix fun String.of(component: Block): Block = component.component(this)

@I18N
fun Block.component(path: String): Block = with(Segment().run(path)) {
    with(this@component.value.find { it.key == result!! }) {
        when (this) {
            null, is Var -> throw Exception("There is no block in block '${this@component.key}' with key = '$result'")
            is Block -> when (rest == "") {
                true -> this
                false -> this.component(rest)
            }
        }
    }
}
