package AdventOfCode.Jul2021.Day7

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day7/Input").readLines()[0].split(",").map { it.toInt() }
    println("Part one: " +
        (input.minOf { it }..input.maxOf { it })
            .map { input.sumOf { c -> abs(c - it) } }
            .minOf { it }
    )

    println("Part two: " +
        (input.minOf { it }..input.maxOf { it })
            .map { input.sumOf { c -> (abs(c - it) * (abs(c - it) + 1)) / 2.0 } }
            .minOf { it }
            .toInt()
    )
}