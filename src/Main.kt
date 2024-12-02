import java.io.File
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    println("Entrez '1' pour analyser 'win.txt', '0' pour continuer, ou 'quit' pour quitter :")

    while (true) {
        val input = scanner.nextLine().trim()
        when {
            input == "1" -> {
                analyzeFile("C:\\Users\\Dell\\Desktop\\win.txt")
            }
            input == "0" -> {
                println("En attente d'une nouvelle entrée...")
            }
            input.equals("quit", ignoreCase = true) -> {
                println("Arrêt du programme.")
                return
            }
            else -> {
                println("Commande non reconnue. Veuillez entrer un argument valide.")
            }
        }
    }
}

fun analyzeFile(filePath: String) {
    try {
        val file = File(filePath)
        if (!file.exists()) {
            println("Erreur : Le fichier '$filePath' n'existe pas.")
            return
        }

        val content = file.readText().trim()
        processSource(content)
    } catch (e: Exception) {
        println("Erreur lors de la lecture du fichier : ${e.message}")
    }
}

fun processSource(source: String) {
    val lexer = Lexer(source)
    lexer.tokenize()

    // Affichage des tokens générés
    println("Tokens générés :")
    lexer.tokens.forEach { token ->
        println(formatToken(token))
    }
    println("Nombre de tokens : ${lexer.tokenCount()}")

    // Analyser toutes les expressions
    val parser = Parser(lexer.tokens)
    val expressions = mutableListOf<Expression>()

    try {
        while (!parser.isAtEnd()) {
            val expression = parser.parse()
            expressions.add(expression)
            println("Expression analysée : ${formatExpression(expression)}")
        }
    } catch (e: RuntimeException) {
        println("Erreur lors de l'analyse : ${e.message}")
    }
}

private fun formatExpression(expression: Expression): String {
    return when (expression) {
        is Binary -> "Binary(left = ${formatExpression(expression.left)}, operator = ${formatToken(expression.operator)}, right = ${formatExpression(expression.right)})"
        is Unary -> "Unary(operator = ${formatToken(expression.operator)}, right = ${formatExpression(expression.right)})"
        is Literal -> "Literal(value=${expression.value})"
        is Grouping -> "Grouping(expression = ${formatExpression(expression.expression)})"
        else -> "Unknown expression"
    }
}

private fun formatToken(token: Token): String {
    return "Token(tokenType=${token.tokenType}, lexeme=\"${token.lexeme}\", literal=${token.literal}, line=${token.line})"
}