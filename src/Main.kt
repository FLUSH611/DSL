import java.util.Scanner
import java.io.File

fun main() {
    val scanner = Scanner(System.`in`)

    // Ajouter un gestionnaire d'interruption pour une sortie propre
    Runtime.getRuntime().addShutdownHook(Thread {
        println("\nProgramme interrompu par l'utilisateur. Arrêt propre.")
    })

    println("Entrez '1' pour afficher le contenu de 'win.txt', '0' ou 'quit' pour quitter, ou deux mots séparés par un espace :")

    try {
        while (true) {
            val input = scanner.nextLine()
            when {
                input == "1" -> {
                    // Lire le contenu du fichier 'win.txt'
                    val filename = "C:\\Users\\Dell\\Desktop\\win.txt" // Chemin complet du fichier
                    val file = File(filename)
                    if (file.exists()) {
                        val content = file.readText().trim()

                        // Afficher le contenu d'origine
                        println("Contenu d'origine du fichier :\n$content")

                        // Décomposer le contenu en tokens
                        val tokens = mutableListOf<Token>()
                        val lines = content.split("\n") // Diviser par lignes

                        // Créer des tokens pour chaque ligne et chaque mot
                        for ((lineNumber, line) in lines.withIndex()) {
                            val words = line.split("\\s+".toRegex()) // Diviser par espaces
                            for (word in words) {
                                if (word.isNotEmpty()) {
                                    // Identifier si le mot est un nombre ou un identifiant
                                    val tokenType = when {
                                        word.matches(Regex("\\d+")) -> TokenType.NUMBER
                                        word.startsWith("\"") && word.endsWith("\"") -> TokenType.STRING
                                        else -> TokenType.IDENTIFIER
                                    }
                                    tokens.add(Token(tokenType, word, word, lineNumber + 1)) // Ligne actuelle
                                }
                            }
                        }

                        // Ajouter un token EOF à la fin
                        tokens.add(Token(TokenType.EOF, "", null, lines.size + 1))

                        // Affichage des tokens
                        println("Tokens générés :")
                        for (token in tokens) {
                            println("TokenType: ${token.tokenType}, Lexeme: '${token.lexeme}', Literal: '${token.literal}', Line: ${token.line}")
                        }
                    } else {
                        println("Le fichier '$filename' n'existe pas.")
                    }
                }
                input == "0" -> {
                    // Indiquer que le programme attend une nouvelle entrée
                    println("Attente d'une nouvelle entrée utilisateur...")
                }
                input.equals("quit", ignoreCase = true) -> {
                    println("Arrêt du programme.")
                    break
                }
                input.toIntOrNull() != null && input.toInt() > 1 -> {
                    // Aide pour les nombres > 1
                    println("Aide: Vous pouvez entrer '1' pour lire le fichier 'win.txt' ou '0'/'quit' pour quitter.")
                }
                input.contains(" ") -> {
                    // Séparer les mots et les afficher sous forme de liste
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