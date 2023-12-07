package AdventOfCode.Jul2021

import java.io.File

fun main() {
    val input = File("src/Input").readLines().map { it.toInt() }
    println("Part one: " + (1 until input.size)
        .fold(0)
        { acc, i -> if (input[i] > input[i - 1]) acc + 1 else acc }
    )
    println("Part two: " + (3 until input.size)
        .fold(0)
        { acc, i ->
            if (input[i] + input[i - 1] + input[i - 2] > input[i - 1] + input[i - 2] + input[i - 3])
                acc + 1
            else
                acc
        }
    )
}