package AdventOfCode.Jul2023

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .mapIndexed { index, line -> Pair(index + 1, line) }
        .map {
            Pair(
                it.first,
                it.second
                    .split(": ")[1]
                    .split(", |; ".toRegex())
                    .map { it.split(" ") }
                    .groupBy { it[1] }
                    .mapValues { it.value.maxOf { it[0].toInt() } },
            )
        }
        .run {
            filter { it.second["red"]!! <= 12 && it.second["green"]!! <= 13 && it.second["blue"]!! <= 14 }
                .sumOf { it.first }
                .let { println("Part one: $it") }
            println("Part two: ${sumOf { it.second["red"]!! * it.second["green"]!! * it.second["blue"]!! }}")
        }
}
