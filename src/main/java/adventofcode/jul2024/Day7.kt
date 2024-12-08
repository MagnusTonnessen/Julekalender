package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .parse()
        .run {
            filter { it.isSolvable(partTwo = false) }
                .sumOf { it.first }
                .printPart1()

            filter { it.isSolvable(partTwo = true) }
                .sumOf { it.first }
                .printPart2()
        }
}

private fun List<String>.parse(): List<Pair<Long, List<Long>>> =
    map {
        it
            .split(": ")
            .let {
                it[0].toLong() to it[1].split(" ").map { it.toLong() }
            }
    }

private fun Pair<Long, List<Long>>.isSolvable(partTwo: Boolean): Boolean =
    second
        .drop(1)
        .fold(listOf(second[0])) { acc, number ->
            acc.flatMap {
                listOfNotNull(
                    it + number,
                    it * number,
                    if (partTwo) (it.toString() + number.toString()).toLong() else null,
                )
            }
        }.contains(first)
