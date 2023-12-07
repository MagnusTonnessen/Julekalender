package AdventOfCode.Jul2023

import java.io.File

val digitMap = mapOf(
    "zero" to "0",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

fun main() {
    File("src/Input")
        .readLines()
        .run {
            map { "${findFirstDigit(it, true)}${findLastDigit(it, true)}" }
                .sumOf { it.toInt() }
                .let { println("Part one: $it") }
            map { "${findFirstDigit(it, false)}${findLastDigit(it, false)}" }
                .sumOf { it.toInt() }
                .let { println("Part two: $it") }
        }
}

fun findFirstDigit(string: String, onlyDigits: Boolean): String {
    val firstDigit = string.find { it.isDigit() }.toString()
    val firstWord = digitMap.keys
        .filter { string.indexOf(it) > -1 }
        .minByOrNull { string.indexOf(it) }
    return when {
        onlyDigits || firstWord == null -> firstDigit
        string.indexOf(firstWord) < string.indexOf(firstDigit) -> digitMap[firstWord]!!
        else -> firstDigit
    }
}

fun findLastDigit(string: String, onlyDigits: Boolean): String {
    val lastDigit = string.findLast { it.isDigit() }.toString()
    val lastWord = digitMap.keys
        .filter { string.lastIndexOf(it) > -1 }
        .maxByOrNull { string.lastIndexOf(it) }
    return when {
        onlyDigits || lastWord == null -> lastDigit
        string.lastIndexOf(lastWord) > string.lastIndexOf(lastDigit) -> digitMap[lastWord]!!
        else -> lastDigit
    }
}
