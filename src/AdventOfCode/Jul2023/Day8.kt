package AdventOfCode.Jul2023

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .run {
            val op = first()
            val map = drop(2).associate {
                Pair(
                    it.substring(0..2),
                    Pair(
                        it.substring(7..9),
                        it.substring(12..14)
                    )
                )
            }
            var node = "AAA"
            var index = 0L
            while (node != "ZZZ") {
                node = when (op[(index % op.length).toInt()]) {
                    'L' -> map[node]!!.first
                    'R' -> map[node]!!.second
                    else -> throw Exception()
                }
                index++
            }
            println("Part one: $index")
            var nodes = map.keys.filter { it.last() == 'A' }
            index = 0
            while (nodes.any { it.last() != 'Z' }) {
                nodes = nodes.map {
                    when (op[(index % op.length).toInt()]) {
                        'L' -> map[it]!!.first
                        'R' -> map[it]!!.second
                        else -> throw Exception()
                    }
                }
                index++
            }
            println("Part two: $index")
        }
}