class Lexer(private var source: String) {
    val tokens = mutableListOf<Token>()
    private var current = 0
    private var line = 1

    fun tokenize() {
        while (!isAtEnd()) {
            val char = advance()
            when {
                char.isWhitespace() -> if (char == '\n') line++
                char.isDigit() -> addToken(TokenType.NUMBER, readNumber())
                char.isLetter() -> addToken(readIdentifier())
                char == '"' -> addToken(TokenType.STRING, readString())
                char == '(' -> addToken(TokenType.LEFT_PAREN, "(")
                char == ')' -> addToken(TokenType.RIGHT_PAREN, ")")
                char == '+' -> addToken(TokenType.PLUS, "+")
                char == '-' -> addToken(TokenType.MINUS, "-")
                char == '*' -> addToken(TokenType.STAR, "*")
                char == '/' -> addToken(TokenType.SLASH, "/")
                char == '&' -> if (match('&')) addToken(TokenType.AND, "&&")
                char == '|' -> if (match('|')) addToken(TokenType.OR, "||")
                char == '>' -> {
                    if (match('=')) addToken(TokenType.GREATER_EQUAL, ">=")
                    else addToken(TokenType.GREATER, ">")
                }
                char == '<' -> {
                    if (match('=')) addToken(TokenType.LESS_EQUAL, "<=")
                    else addToken(TokenType.LESS, "<")
                }
                else -> println("Warning: Unrecognized character '$char' at line $line. Skipping.")
            }
        }
        tokens.add(Token(TokenType.EOF, "", null, line))
    }

    private fun advance(): Char {
        return if (!isAtEnd()) source[current++] else '\u0000'
    }

    private fun isAtEnd() = current >= source.length

    private fun addToken(type: TokenType, lexeme: String) {
        val token = Token(type, lexeme, null, line)
        tokens.add(token)
    }

    private fun addToken(type: TokenType) {
        addToken(type, "")
    }

    private fun readNumber(): String {
        val start = current - 1
        while (current < source.length && source[current].isDigit()) advance()
        return source.substring(start, current)
    }

    private fun readString(): String {
        val start = current
        while (!isAtEnd() && peekChar() != '"') {
            if (peekChar() == '\n') line++
            advance()
        }
        advance() // Consommer la quote fermante
        return source.substring(start, current - 1)
    }

    private fun readIdentifier(): TokenType {
        val start = current - 1
        while (current < source.length && (source[current].isLetterOrDigit() || source[current] == '_')) {
            advance()
        }
        val identifier = source.substring(start, current)
        return isKeyword(identifier) ?: TokenType.IDENTIFIER
    }

    private fun isKeyword(lexeme: String): TokenType? {
        return when (lexeme) {
            "true" -> TokenType.TRUE
            "false" -> TokenType.FALSE
            "null" -> TokenType.NULL
            else -> null
        }
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false
        current++
        return true
    }

    private fun peekChar(): Char {
        return if (isAtEnd()) '\u0000' else source[current]
    }

    fun tokenCount(): Int {
        return tokens.size
    }
}