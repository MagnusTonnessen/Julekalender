package adventofcode.jul2023

import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .map { it.split(" ").map { it.toLong() } }
        .run {
            fold(Pair(0L, 0L)) { sum, nextList ->
                val lists = mutableListOf(nextList)
                while (lists.first().any { it != 0L }) {
                    lists.add(0, lists.first().zipWithNext { a, b -> b - a })
                }
                lists
                    .fold(Pair(0L, 0L)) { acc, it -> Pair(acc.first + it.last(), it.first() - acc.second) }
                    .let { Pair(sum.first + it.first, sum.second + it.second) }
            }.let {
                println("Part one: ${it.first}")
                println("Part two: ${it.second}")
            }
        }
}
