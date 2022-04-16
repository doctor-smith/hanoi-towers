package lib.language

interface Language {
    val locale: String
}



sealed class Lang {
    data class Variable(
        val name: String,
        val value: String
    ) : Lang()
    data class Block(
        val name: String,
        val content: List<Lang>
    ) : Lang()
}

operator fun Lang?.get(name: String): Lang? = when(this) {
    null -> null
    is Lang.Variable -> this
    is Lang.Block -> content.find { this.name == name }
}

//fun load(locale: String): Lang?