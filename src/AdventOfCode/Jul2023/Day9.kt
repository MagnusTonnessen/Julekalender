package AdventOfCode.Jul2023

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .map { it.split(" ").map { it.toLong() } }
        .run {
            fold(Pair(0L, 0L)) { acc, it ->
                var list = it
                var next = 0L
                var prev = 0L
                while (list.any { it != 0L }) {
                    list = list
                        .zipWithNext { a, b -> b - a }
                        .reversed()
                        .apply {
                            next += last()
                            prev = first() - prev
                        }
                }
                Pair(acc.first + next, acc.second + prev)
            }.let {
                println("Part one: ${it.first}")
                println("Part two: ${it.second}")
            }
        }
}