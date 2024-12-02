class State(private val source: String) {
    var current: Int = 0
    var line: Int = 1

    fun isAtEnd() = current >= source.length

    fun advance() {
        if (peekChar() == '\n') line++
        current++
    }

    fun peekChar(): Char = if (isAtEnd()) '\u0000' else source[current]

    fun peekNextChar(): Char = if (current + 1 >= source.length) '\u0000' else source[current + 1]

    fun readNumber(): String {
        val start = current
        while (current < source.length && source[current].isDigit()) advance()
        return source.substring(start, current)
    }

    fun readString(): String {
        advance() // Consomme le guillemet d'ouverture
        val start = current
        while (!isAtEnd() && peekChar() != '"') {
            if (peekChar() == '\n') line++
            advance()
        }
        advance() // Consomme le guillemet de fermeture
        return source.substring(start, current - 1)
    }

    fun readIdentifier(): String {
        val start = current
        while (current < source.length && (source[current].isLetter() || source[current].isDigit())) advance()
        return source.substring(start, current)
    }
}