class Lexer(private val source: String) {
    private var current = 0
    private var line = 1
    private val comments = mutableListOf<String>()

    fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()

        while (!isAtEnd()) {
            when (val char = advance()) {
                '(' -> tokens.add(Token(TokenType.LEFT_PAREN, "(", null, line))
                ')' -> tokens.add(Token(TokenType.RIGHT_PAREN, ")", null, line))
                '{' -> tokens.add(Token(TokenType.LEFT_BRACE, "{", null, line))
                '}' -> tokens.add(Token(TokenType.RIGHT_BRACE, "}", null, line))
                ',' -> tokens.add(Token(TokenType.COMMA, ",", null, line))
                '.' -> tokens.add(Token(TokenType.DOT, ".", null, line))
                '-' -> tokens.add(Token(TokenType.MINUS, "-", null, line))
                '+' -> tokens.add(Token(TokenType.PLUS, "+", null, line))
                ';' -> tokens.add(Token(TokenType.SEMICOLON, ";", null, line))
                '/' -> {
                    if (peek() == '/') {
                        val comment = handleSingleLineComment()
                        comments.add(comment)
                    } else if (peek() == '*') {
                        val comment = handleMultiLineComment()
                        comments.add(comment)
                    } else {
                        tokens.add(Token(TokenType.SLASH, "/", null, line))
                    }
                }
                '*' -> tokens.add(Token(TokenType.STAR, "*", null, line))
                '=' -> tokens.add(Token(TokenType.EQUAL, "=", null, line))
                ' ' -> {} // Ignorer les espaces
                '\n' -> line++
                '"' -> tokens.add(Token(TokenType.STRING, readString(), null, line))
                in '0'..'9' -> tokens.add(Token(TokenType.NUMBER, readNumber(), null, line))
                '>' -> tokens.add(Token(TokenType.GREATER, ">", null, line))
                else -> {
                    if (isAlpha(char)) {
                        val identifier = readIdentifier()
                        val type = when (identifier) {
                            "fun" -> TokenType.FUN
                            "var" -> TokenType.VAR
                            else -> TokenType.IDENTIFIER
                        }
                        tokens.add(Token(type, identifier, null, line))
                    } else {
                        println("Erreur à la ligne $line : caractère inattendu '$char'")
                    }
                }
            }
        }
        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun handleSingleLineComment(): String {
        val comment = StringBuilder()
        while (!isAtEnd() && peek() != '\n') {
            comment.append(advance())
        }
        return "// ${comment.toString().trim()}"
    }

    private fun handleMultiLineComment(): String {
        advance() // Consomme le '*'
        val comment = StringBuilder()
        while (!isAtEnd()) {
            if (peek() == '*' && peekNext() == '/') {
                advance() // Consomme le '*'
                advance() // Consomme le '/'
                return "/* ${comment.toString().trim()} */"
            }
            if (peek() == '\n') line++
            comment.append(advance())
        }
        println("Erreur à la ligne $line : commentaire multi-ligne non fermé")
        return "/* commentaire multi-ligne non fermé */"
    }

    fun getComments(): List<String> = comments

    private fun isAtEnd() = current >= source.length
    private fun advance() = source[current++]

    private fun readString(): String {
        val start = current
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++
            advance()
        }
        if (!isAtEnd()) advance() // Consommer le guillemet fermant
        return source.substring(start, current)
    }

    private fun readNumber(): String {
        val start = current - 1
        while (isDigit(peek())) advance()
        return source.substring(start, current)
    }

    private fun readIdentifier(): String {
        val start = current - 1
        while (isAlpha(peek())) advance()
        return source.substring(start, current)
    }

    private fun peek() = if (isAtEnd()) '\u0000' else source[current]

    private fun peekNext(): Char {
        return if (current + 1 >= source.length) '\u0000' else source[current + 1]
    }

    private fun isAlpha(c: Char) = c.isLetter() || c == '_'
    private fun isDigit(c: Char) = c.isDigit()
}