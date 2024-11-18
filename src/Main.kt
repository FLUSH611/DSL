import java.util.Scanner
import java.io.File

fun main() {
    val scanner = Scanner(System.`in`)

    Runtime.getRuntime().addShutdownHook(Thread {
        println("\nProgramme interrompu par l'utilisateur. Arrêt propre.")
    })

    println("Entrez '1' pour afficher le contenu de 'win.txt', '0' ou 'quit' pour quitter, ou deux mots séparés par un espace :")

    try {
        while (true) {
            val input = scanner.nextLine().trim()
            when {
                input == "1" -> {
                    val filename = "C:\\Users\\Dell\\Desktop\\win.txt"
                    val file = File(filename)
                    if (file.exists()) {
                        val content = file.readText().trim()

                        println("Contenu d'origine du fichier :\n$content")

                        val lexer = Lexer(content)
                        val tokens = lexer.tokenize()

                        println("Tokens générés :")
                        for (token in tokens) {
                            if (token.tokenType != TokenType.COMMENT) {
                                println("TokenType: ${token.tokenType}, Lexeme: '${token.lexeme}', Line: ${token.line}")
                            }
                        }

                        println("Commentaires ignorés :")
                        for (comment in lexer.getComments()) {
                            println(comment)
                        }

                        println("Nombre total de tokens: ${tokens.size}")
                    } else {
                        println("Le fichier '$filename' n'existe pas.")
                    }
                }
                input == "0" -> {
                    println("Attente d'une nouvelle entrée utilisateur...")
                }
                input.equals("quit", ignoreCase = true) -> {
                    println("Arrêt du programme.")
                    break
                }
                input.toIntOrNull() != null && input.toInt() > 1 -> {
                    println("Aide: Vous pouvez entrer '1' pour lire le fichier 'win.txt' ou '0'/'quit' pour quitter.")
                }
                input.contains(" ") -> {
                    val words = input.split(" ")
                    println("Liste des mots : $words")
                }
                else -> {
                    println("Commande non reconnue. Veuillez entrer un argument valide.")
                }
            }
        }
    } catch (e: Exception) {
        println("Le programme a été interrompu : ${e.message}")
    }
}