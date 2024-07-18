package search
import kotlin.system.exitProcess
import java.io.File

fun main(args: Array<String>) {
    SimpleSearchEngine(args)
}

class SimpleSearchEngine(args: Array<String>) {
    private val dataLines = mutableListOf<String>()

    init {
        if (args.contains("--data")) {
            val fileNameIndex = args.indexOf("--data") + 1
            if (fileNameIndex < args.size) {
                val fileName = args[fileNameIndex]
                dataLines.addAll(File(fileName).readLines())
            }
        }
        menu()
    }

    private fun menu() {
        do {
            println("\n=== Menu ===")
            println(
                "1. Find a person\n" +
                        "2. Print all people\n" +
                        "0. Exit"
            )

            when (readln().toInt()) {
                0 -> {
                    println("Bye!")
                    exitProcess(0)
                }
                1 -> search()
                2 -> displayAllPeople()
                else -> println("Incorrect option! Try again.")
            }
        } while (true)
    }

    private fun search() {
        println("Enter a name or email to search all suitable people.")
        val query = readln().lowercase()
        val results = dataLines.filter { it.lowercase().contains(query) }

        if (results.isNotEmpty()) {
            println("People found:")
            results.forEach { println(it) }
        } else {
            println("No matching people found.")
        }
    }

    private fun displayAllPeople() {
        println("\n=== List of people ===")
        dataLines.forEach { println(it) }
    }
}


