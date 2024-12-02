sealed class Expression

data class Binary(val left: Expression, val operator: Token, val right: Expression) : Expression()
data class Unary(val operator: Token, val right: Expression) : Expression()
data class Literal(val value: Any?) : Expression()
data class Grouping(val expression: Expression) : Expression()
//classxpresion