import java.io.File
import java.util.*
data class Program(val expressions: List<Expression>) //main
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

        val lines = file.readLines()
        val expressions = mutableListOf<Expression>()

        for (line in lines) {
            val expression = processSource(line.trim())
            if (expression != null) {
                expressions.add(expression)
            }
        }

        // Créer un programme avec toutes les expressions
        val program = Program(expressions)
        println("Programme analysé : ${formatProgram(program)}")

    } catch (e: Exception) {
        println("Erreur lors de la lecture du fichier : ${e.message}")
    }
}

fun processSource(source: String): Expression? {
    val lexer = Lexer(source)
    lexer.tokenize()

    // Affichage des tokens générés
    println("Tokens générés pour l'expression '$source':")
    lexer.tokens.forEach { token ->
        println(formatToken(token))
    }
    println("Nombre de tokens : ${lexer.tokenCount()}")

    // Analyser une seule expression
    val parser = Parser(lexer.tokens)

    return try {
        parser.parse()
    } catch (e: RuntimeException) {
        println("Erreur lors de l'analyse de '$source': ${e.message}")
        null
    }
}

private fun formatProgram(program: Program): String {
    return "Program(expressions = [${program.expressions.joinToString(", ") { formatExpression(it) }}])"
}

private fun formatExpression(expression: Expression): String {
    return when (expression) {
        is Binary -> "Binary(left = ${formatExpression(expression.left)}, operator = ${formatToken(expression.operator)}, right = ${formatExpression(expression.right)})"
        is Unary -> "Unary(operator = ${formatToken(expression.operator)}, right = ${formatExpression(expression.right)})"
        is Literal -> "Literal(value=${expression.value})"
        is Grouping -> "Grouping(expression = ${formatExpression(expression.expression)})"
    }
}

private fun formatToken(token: Token): String {
    return "Token(tokenType=${token.tokenType}, lexeme=\"${token.lexeme}\", literal=${token.literal}, line=${token.line})"
}
