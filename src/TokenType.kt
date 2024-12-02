enum class TokenType {
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
    BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL, GREATER,
    GREATER_EQUAL, LESS, LESS_EQUAL,
    IDENTIFIER, STRING, NUMBER, COMMENT,
    AND, CLASS, ELSE, FALSE, FUN, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, EOF, GREATER_THAN;

    companion object {
        fun fromIdentifier(identifier: String): TokenType {
            return when (identifier) {
                "and" -> AND
                "class" -> CLASS
                "else" -> ELSE
                "false" -> FALSE
                "fun" -> FUN
                "if" -> IF
                "nil" -> NIL
                "or" -> OR
                "print" -> PRINT
                "return" -> RETURN
                "super" -> SUPER
                "this" -> THIS
                "true" -> TRUE
                else -> IDENTIFIER
            }
        }
    }
}