package search

fun main() {
    SimpleSearchEngine()
}

class SimpleSearchEngine {

    init {
        val sequenceOfWords = readlnOrNull()?.split("\\s".toRegex())
        val wordToFind = readlnOrNull()
        var indexOfWord = "Not found"
        sequenceOfWords?.forEachIndexed { index, s ->
            if (s.equals(wordToFind,true) )  indexOfWord = "${index + 1}"
        }

        println(indexOfWord)
    }
}

