import java.io.File
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    println("Entrez '1' pour afficher le contenu de 'win.txt', '0' ou 'quit' pour quitter, ou deux mots séparés par un espace :")

    while (true) {
        val input = scanner.nextLine().trim()
        if (!handleInput(input)) break
    }
}

fun handleInput(input: String): Boolean {
    return when {
        input == "1" -> {
            handleFileInput("C:\\Users\\Dell\\Desktop\\win.txt")
            true
        }
        input == "0" -> {
            println("Attente d'une nouvelle entrée utilisateur...")
            true
        }
        input.equals("quit", ignoreCase = true) -> {
            println("Arrêt du programme.")
            false
        }
        input.contains(" ") -> {
            val words = input.split(" ")
            println("Liste des mots : $words")
            true
        }
        else -> {
            println("Commande non reconnue. Veuillez entrer un argument valide.")
            true
        }
    }
}

fun handleFileInput(filename: String) {
    try {
        val file = File(filename)
        if (file.exists()) {
            val content = file.readText().trim()
            processSource(content)
        } else {
            println("Erreur : Le fichier '$filename' n'existe pas.")
        }
    } catch (e: Exception) {
        println("Erreur lors de la lecture du fichier : ${e.message}")
    }
}

fun processSource(source: String) {
    val lexer = Lexer(source)
    lexer.tokenize()

    println("Tokens générés :")
    lexer.tokens.forEach { println(it) }
    println("Nombre de tokens : ${lexer.tokenCount()}")
}