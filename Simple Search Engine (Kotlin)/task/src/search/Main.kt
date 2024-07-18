package search

import kotlin.system.exitProcess

fun main() {
    SimpleSearchEngine()
}

class SimpleSearchEngine {
    val dataLines = mutableListOf<String>()

    init {
        menu()
    }

    private fun menu() {
        println("Enter the number of people:")
        val numberOfPeople = readln().toInt()

        println("Enter all people:")
        repeat(numberOfPeople) {
            dataLines.add(readln())
        }
        do {
            println("\n=== Menu ===")
            println(
                "1. Find a person\n" +
                        "2. Print all people\n" +
                        "0. Exit"
            )

            val userInput = readln().toInt()
            when (userInput) {
                0 -> {
                    println("\nBye!")
                    exitProcess(0)
                }
                1 -> search()
                2 -> displayAllPeople()
                else -> {
                    println("Incorrect option! Try again.")
                }

            }

        } while (userInput != 0)


    }

    private fun search() {
            println("Enter a name or email to search all suitable people.")
            val data = readln().lowercase()
            val results = dataLines.filter { it.lowercase().contains(data) }

            if (results.isNotEmpty()) {
                println("People found:")
                results.forEach { println(it) }
            } else {
                println("No matching people found.")
            }

    }

    private fun displayAllPeople() {
        println("\n=== List of people ===\n${dataLines.joinToString("\n")}")
    }


}

