package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .map { it.split(" ").map { it.toLong() } }
        .run {
            count { it.isSafe() }.printPart1()

            count {
                it.indices.any { index ->
                    it
                        .filterIndexed { i, _ -> i != index }
                        .isSafe()
                }
            }.printPart2()
        }
}

private fun List<Long>.isSafe(): Boolean =
    windowed(2)
        .run {
            all { it[0] in it[1] + 1..it[1] + 3 } || all { it[0] in it[1] - 3 until it[1] }
        }
