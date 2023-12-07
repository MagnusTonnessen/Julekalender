package AdventOfCode.Jul2023

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .map { it.split(Regex(" +")).drop(1).map { it.toLong() } }
        .run {
            let { it.first().zip(it.last()) }
                .map { (t, d) -> (0..t).count { it * (t - it) > d } }
                .reduce { acc, i -> acc * i }
                .let { println("Part one: $it") }
            map { it.joinToString("").toLong() }
                .let { (t, d) -> (0..t).count { it * (t - it) > d } }
                .let { println("Part two: $it") }
        }
}
