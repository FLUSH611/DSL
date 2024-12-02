class Parser(private val tokens: List<Token>) {
    private var current = 0

    fun parse(): Expression {
        return expression()
    }

    private fun expression(): Expression {
        return logicalOr()
    }

    private fun logicalOr(): Expression {
        var expr = logicalAnd()
        while (match(TokenType.OR)) {
            val operator = previous()
            val right = logicalAnd()
            expr = Binary(expr, operator, right)
        }
        return expr
    }

    private fun logicalAnd(): Expression {
        var expr = equality()
        while (match(TokenType.AND)) {
            val operator = previous()
            val right = equality()
            expr = Binary(expr, operator, right)
        }
        return expr
    }

    private fun equality(): Expression {
        var expr = comparison()
        while (match(TokenType.EQUAL, TokenType.NOT_EQUAL)) {
            val operator = previous()
            val right = comparison()
            expr = Binary(expr, operator, right)
        }
        return expr
    }

    private fun comparison(): Expression {
        var expr = term()
        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            val operator = previous()
            val right = term()
            expr = Binary(expr, operator, right)
        }
        return expr
    }

    private fun term(): Expression {
        var expr = factor()
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            val operator = previous()
            val right = factor()
            expr = Binary(expr, operator, right)
        }
        return expr
    }

    private fun factor(): Expression {
        var expr = unary()
        while (match(TokenType.STAR, TokenType.SLASH)) {
            val operator = previous()
            val right = unary()
            expr = Binary(expr, operator, right)
        }
        return expr
    }

    private fun unary(): Expression {
        if (match(TokenType.MINUS)) {
            val operator = previous()
            val right = unary()
            return Unary(operator, right)
        }
        return primary()
    }

    private fun primary(): Expression {
        return when {
            match(TokenType.NUMBER) -> Literal(previous().lexeme.toDouble())
            match(TokenType.TRUE) -> Literal(true)
            match(TokenType.FALSE) -> Literal(false)
            match(TokenType.NULL) -> Literal(null)
            match(TokenType.STRING) -> Literal(previous().lexeme)
            match(TokenType.LEFT_PAREN) -> {
                val expr = expression()
                consume(TokenType.RIGHT_PAREN, "Expected ')' after expression.")
                Grouping(expr)
            }
            match(TokenType.IDENTIFIER) -> Literal(previous().lexeme)
            else -> throw RuntimeException("Expected expression, found '${peek().lexeme}' instead.")
        }
    }

    private fun match(vararg types: TokenType): Boolean {
        for (type in types) {
            if (check(type)) {
                advance()
                return true
            }
        }
        return false
    }

    private fun check(type: TokenType): Boolean {
        if (isAtEnd()) return false
        return peek().tokenType == type
    }

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    fun isAtEnd(): Boolean {
        return peek().tokenType == TokenType.EOF
    }

    private fun peek(): Token {
        return tokens[current]
    }

    private fun previous(): Token {
        return tokens[current - 1]
    }

    private fun consume(type: TokenType, message: String) {
        if (check(type)) {
            advance()
        } else {
            throw RuntimeException(message)
        }
    }
}