class Lexer(private val source: String) {
    val tokens = mutableListOf<Token>()
    private var current = 0
    private var line = 1

    fun tokenize() {
        while (!isAtEnd()) {
            val char = advance()
            when {
                char.isWhitespace() -> {
                    if (char == '\n') line++
                    continue // Ignore whitespace
                }
                char.isDigit() -> addToken(TokenType.NUMBER, readNumber())
                char.isLetter() -> addToken(TokenType.IDENTIFIER, readIdentifier())
                char == '"' -> addToken(TokenType.STRING, readString())
                char == '/' -> handleSlash()
                char == '=' -> addToken(TokenType.EQUAL, "=") // Utilisation correcte
                char == '(' -> addToken(TokenType.LEFT_PAREN, "(")
                char == ')' -> addToken(TokenType.RIGHT_PAREN, ")")
                char == '{' -> addToken(TokenType.LEFT_BRACE, "{")
                char == '}' -> addToken(TokenType.RIGHT_BRACE, "}")
                char == ',' -> addToken(TokenType.COMMA, ",")
                char == '>' -> addToken(TokenType.GREATER_THAN, ">")
                char == '+' -> addToken(TokenType.PLUS, "+")
                char == '*' -> addToken(TokenType.STAR, "*")
                else -> {
                    // Skip unrecognized characters
                    println("Warning: Unrecognized character '${char}' at line $line. Skipping.")
                }
            }
        }
        tokens.add(Token(TokenType.EOF, "", null, line)) // Add EOF token
    }

    private fun advance(): Char {
        return if (!isAtEnd()) source[current++] else '\u0000'
    }

    private fun isAtEnd() = current >= source.length

    private fun addToken(type: TokenType, lexeme: String) {
        val token = Token(type, lexeme, null, line)
        tokens.add(token)
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
        advance() // Consomme le guillemet de fermeture
        return source.substring(start, current - 1)
    }

    private fun readIdentifier(): String {
        val start = current - 1
        while (current < source.length && (source[current].isLetter() || source[current].isDigit())) advance()
        return source.substring(start, current)
    }

    private fun peekChar(): Char {
        return if (isAtEnd()) '\u0000' else source[current]
    }

    private fun handleSlash() {
        if (peekChar() == '/') { // Commentaire sur une seule ligne
            advance() // Consomme le premier slash
            advance() //  le second slash
            while (!isAtEnd() && peekChar() != '\n') {
                advance() // Ignore le contenu du commentaire
            }
        } else if (peekChar() == '*') { // Commentaire multi-ligne
            advance() // Consomme le premier slash
            advance() // Consomme l'astérisque
            while (!isAtEnd()) {
                if (peekChar() == '*' && peekNextChar() == '/') {
                    advance() // Consomme l'astérisque
                    advance() // Consomme le slash
                    break // Fin du commentaire multi-ligne
                }
                if (peekChar() == '\n') line++ // Compte les nouvelles lignes
                advance() // Ignore le contenu du commentaire
            }
        } else {
            addToken(TokenType.SLASH, "/") // Si ce n'est pas un commentaire
        }
    }
    private fun peekNextChar(): Char {
        return if (current + 1 >= source.length) '\u0000' else source[current + 1]
    }

    fun tokenCount(): Int {
        return tokens.size
    }
}