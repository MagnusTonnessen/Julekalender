package adventofcode.jul2022

import java.io.File

fun main() {
    val input =
        File("src/Input.txt")
            .readLines()
            .map { line -> line.split(",").map { range -> range.split("-").map { it.toInt() } } }
    input
        .count {
            it[0][0] >= it[1][0] && it[0][1] <= it[1][1] || it[1][0] >= it[0][0] && it[1][1] <= it[0][1]
        }.also { println("Part one: $it") }
    input
        .count {
            it[0][0] >= it[1][0] && it[0][0] <= it[1][1] || it[0][0] <= it[1][0] && it[0][1] >= it[1][0]
        }.also { println("Part two: $it") }
}
