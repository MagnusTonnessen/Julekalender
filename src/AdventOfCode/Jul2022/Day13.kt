package AdventOfCode.Jul2022

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .windowed(2, 3)
        .also { it ->
            it.withIndex()
                .filter { compare(it.value[0], it.value[1]) }
                .sumOf { it.index + 1 }
                .also { println("Part one: $it") }
            it.flatten()
                .toMutableList()
                .apply { addAll(listOf("[[2]]", "[[6]]")) }
                .sortedWith { string1, string2 -> if (compare(string1, string2)) 1 else -1 }
                .reversed()
                .withIndex()
                .filter { it.value == "[[2]]" || it.value == "[[6]]" }
                .fold(1) { acc, pair -> acc * (pair.index + 1) }
                .also { println("Part two: $it") }

        }
}

fun compare(string1: String, string2: String): Boolean {
    return when {
        string1.first() == '[' && string2.first().isDigit() -> compare(string1, addBracket(string2))
        string1.first().isDigit() && string2.first() == '[' -> compare(addBracket(string1), string2)
        string1.first() == ']' && string2.first() != ']' -> true
        string1.first() != ']' && string2.first() == ']' -> false
        getNum(string1) == getNum(string2) && getNum(string1) != null -> compare(string1.substringAfter(getNum(string1).toString()), string2.substringAfter(
            getNum(string2).toString()))
        getNum(string1) != null && getNum(string2) != null -> getNum(string1)!! < getNum(string2)!!
        else -> compare(string1.drop(1), string2.drop(1))
    }
}

fun addBracket(string: String): String {
    return "[${getNum(string)}]${string.drop(getNum(string).toString().length)}"
}

fun getNum(string: String): Int? {
    return try {
        string.takeWhile { it.isDigit() }.toInt()
    } catch (e: Exception) {
        null
    }
}
