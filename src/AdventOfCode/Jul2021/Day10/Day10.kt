package AdventOfCode.Jul2021.Day10

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day10/Input").readLines()

    val mapCorrupted = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val mapComplete = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
    val corrupted = mutableListOf<Char>()
    val complete = mutableListOf<Long>()

    for (line in input) {
        var copy = line
        while (copy.contains(Regex("\\(\\)|\\[]|\\{}|<>"))) {
            copy = copy.replace(Regex("\\(\\)|\\[]|\\{}|<>"), "")
        }
        if (isCorrupted(copy)) {
            corrupted.add(copy.first { it !in listOf('(', '[', '{', '<') })
        } else {
            complete.add(copy.reversed().fold(0) { acc, c -> acc * 5 + mapComplete[c]!! })
        }
    }

    println("Corrupted: ${corrupted.sumOf { mapCorrupted[it]!! }}")
    println("Complete: ${complete.sorted()[complete.size / 2]}")
}

fun isCorrupted(string: String): Boolean {
    return string.contains(Regex("\\(]|\\(}|\\(>|\\[\\)|\\[}|\\[>|\\{\\)|\\{]|\\{>|<\\)|<]|<}"))
}