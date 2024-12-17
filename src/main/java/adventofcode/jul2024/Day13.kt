package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .windowed(3, 4)
        .map {
            it
                .map {
                    it
                        .substringAfter(": ")
                        .replace("[XY+=]".toRegex(), "")
                        .split(", ")
                        .map { it.toLong() }
                }.flatten()
        }.run {
            mapNotNull { cramersRule(it.take(4), it[4], it[5]) }
                .sumOf { 3 * it.first + it.second }
                .printPart1()

            mapNotNull { cramersRule(it.take(4), it [4] + 10000000000000, it[5] + 10000000000000) }
                .sumOf { 3 * it.first + it.second }
                .printPart2()
        }
}

fun cramersRule(
    eq: List<Long>,
    c1: Long,
    c2: Long,
): Pair<Long, Long>? {
    val (a1, a2, b1, b2) = eq
    val determinant = a1 * b2 - a2 * b1
    if (determinant == 0L) {
        return null
    }

    val x = (c1 * b2 - c2 * b1).toDouble() / determinant
    val y = (a1 * c2 - a2 * c1).toDouble() / determinant

    return if (x % 1 == 0.0 && y % 1 == 0.0) {
        x.toLong() to y.toLong()
    } else {
        null
    }
}
