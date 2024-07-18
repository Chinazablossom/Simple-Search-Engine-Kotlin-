package search

import kotlin.system.exitProcess
import java.io.File

fun main(args: Array<String>) {
    SimpleSearchEngine(args)
}

class SimpleSearchEngine(args: Array<String>) {
    private val dataLines = mutableListOf<String>()
    private val invertedIndex = mutableMapOf<String, MutableSet<Int>>()

    init {
        if (args.contains("--data")) {
            val fileNameIndex = args.indexOf("--data") + 1
            if (fileNameIndex < args.size) {
                val fileName = args[fileNameIndex]
                dataLines.addAll(File(fileName).readLines())
                buildInvertedIndex()
            }
        }
        menu()
    }

    private fun buildInvertedIndex() {
        dataLines.forEachIndexed { index, line ->
            line.split("\\s".toRegex()).forEach { word ->
                val cleanWord = word.lowercase().trim()
                if (cleanWord.isNotEmpty()) {
                    invertedIndex.computeIfAbsent(cleanWord) { mutableSetOf() }.add(index)
                }
            }
        }
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
        val query = readln().lowercase().trim()
        val indices = invertedIndex[query]

        if (indices != null && indices.isNotEmpty()) {
            println("People found:")
            indices.forEach { index ->
                println(dataLines[index])
            }
        } else {
            println("No matching people found.")
        }
    }

    private fun displayAllPeople() {
        println("\n=== List of people ===")
        dataLines.forEach { println(it) }
    }
}
