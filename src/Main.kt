import java.util.Scanner
import java.io.File

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // Ajouter un gestionnaire d'interruption pour une sortie propre
    Runtime.getRuntime().addShutdownHook(Thread {
        println("\nProgramme interrompu par l'utilisateur. Arrêt propre.")
    })

    println("Entrez '1' pour afficher le contenu de 'win.txt', '0' ou 'quit' pour quitter :")

    try {
        while (true) {
            val input = scanner.nextLine()
            when {
                input == "1" -> {
                    // Lire le contenu du fichier 'win.txt' à partir du chemin spécifié
                    val filename = "C:\\Users\\Dell\\Desktop\\win.txt" // Chemin complet du fichier
                    val file = File(filename)
                    if (file.exists()) {
                        val content = file.readText()
                        println("Contenu du fichier '$filename':\n$content")
                    } else {
                        println("Le fichier '$filename' n'existe pas.")
                    }
                }
                input == "0" || input.equals("quit", ignoreCase = true) -> {
                    println("Arrêt du programme.")
                    break
                }
                input.toIntOrNull() != null && input.toInt() > 1 -> {
                    // Aide pour les nombres > 1
                    println("Aide: Vous pouvez entrer '1' pour lire le fichier 'win.txt' ou '0'/'quit' pour quitter.")
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