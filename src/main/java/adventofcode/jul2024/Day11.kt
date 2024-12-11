package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt").readText().split(" ").run {
        sumOf { it.count(25) }.printPart1()
        sumOf { it.count(75) }.printPart2()
    }
}

private val cache = mutableMapOf<Pair<String, Int>, Long>()

private fun String.count(n: Int): Long {
    val key = this to n
    return cache.getOrPut(key) {
        when {
            n == 0 -> 1L
            this == "0" -> "1".count(n - 1)
            this.length % 2 == 0 -> this.chunked(this.length / 2).sumOf { it.toLong().toString().count(n - 1) }
            else -> (this.toLong() * 2024).toString().count(n - 1)
        }
    }
}
