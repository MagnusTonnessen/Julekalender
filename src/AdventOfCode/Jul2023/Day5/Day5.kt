package AdventOfCode.Jul2023.Day5

import java.io.File

fun main() {
    File("src/AdventOfCode/Jul2023/Day5/Input")
        .readText()
        .split("\n\n")
        .map { it.lines().filter { it.none { it.isLetter() } }.map { it.split(" ").map { it.toLong() } } }
        .run {
            first()
                .first()
                .minOf {
                    drop(1).fold(it) { acc, map ->
                        map.firstOrNull { acc in it[1]..it[1] + it[2] }?.let { it[0] + (acc - it[1]) } ?: acc
                    }
                }
                .let { println("Part one: $it") }

            first()
                .first()
                .windowed(2, 2)
                .minOf {
                    (it[0] until it[0] + it[1]).minOf {
                        drop(1).fold(it) { acc, map ->
                            map.firstOrNull { acc in it[1]..it[1] + it[2] }?.let { it[0] + (acc - it[1]) } ?: acc
                        }
                    }
                }
                .let { println("Part two: $it") }
        }
}
