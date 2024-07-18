package search

fun main() {
    SimpleSearchEngine()
}

class SimpleSearchEngine {

    init {

        println("Enter the number of people:")
        val numberOfPeople = readln().toInt()
        val dataLines = mutableListOf<String>()

        println("Enter all people:")
        repeat(numberOfPeople) {
            dataLines.add(readln())
        }
        println("\nEnter the number of search queries:")
        val numOfSearchQueries = readln().toInt()

        repeat(numOfSearchQueries) {
            println("Enter data to search people:")
            val data = readln().lowercase()
            val results = dataLines.filter { it.lowercase().contains(data) }

            if (results.isNotEmpty()) {
                println("People found:")
                results.forEach { println(it) }
            } else {
                println("No matching people found.")
            }
        }


    }
}

