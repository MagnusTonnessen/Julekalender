package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File
import kotlin.math.abs

fun main() {
    File("src/Input.txt")
        .readLines()
        .map { it.split("   ").map { it.toLong() } }
        .fold(emptyList<Long>() to emptyList<Long>()) { (l, r), (a, b) ->
            (l + a).sorted() to (r + b).sorted()
        }.run {
            first
                .zip(second)
                .sumOf { (a, b) -> abs(a - b) }
                .printPart1()

            first
                .sumOf { a -> a * second.count { it == a } }
                .printPart2()
        }
}
