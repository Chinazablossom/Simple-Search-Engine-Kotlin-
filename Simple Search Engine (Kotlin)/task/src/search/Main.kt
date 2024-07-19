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
        println("Select a matching strategy: ALL, ANY, NONE")
        val strategy = readln().uppercase().trim()
        println("Enter a name or email to search all suitable people.")
        val query = readln().lowercase().trim().split(" ")

        val results = when (strategy) {
            "ALL" -> searchAll(query)
            "ANY" -> searchAny(query)
            "NONE" -> searchNone(query)
            else -> {
                println("Unknown strategy! Defaulting to ANY.")
                searchAny(query)
            }
        }

        if (results.isNotEmpty()) {
            println("${results.size} persons found:")
            results.forEach { println(it) }
        } else {
            println("No matching people found.")
        }
    }

    private fun searchAll(query: List<String>): List<String> {
        return dataLines.filterIndexed { index, _ ->
            query.all { invertedIndex[it]?.contains(index) == true }
        }
    }

    private fun searchAny(query: List<String>): List<String> {
        val indices = mutableSetOf<Int>()
        query.forEach { word ->
            invertedIndex[word]?.let { indices.addAll(it) }
        }
        return indices.map { dataLines[it] }
    }

    private fun searchNone(query: List<String>): List<String> {
        val indicesToExclude = mutableSetOf<Int>()
        query.forEach { word ->
            invertedIndex[word]?.let { indicesToExclude.addAll(it) }
        }
        return dataLines.filterIndexed { index, _ ->
            index !in indicesToExclude
        }
    }

    private fun displayAllPeople() {
        println("\n=== List of people ===")
        dataLines.forEach { println(it) }
    }
}
