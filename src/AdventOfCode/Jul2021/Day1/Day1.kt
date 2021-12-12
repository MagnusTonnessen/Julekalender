package AdventOfCode.Jul2021.Day1

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day1/Input").readLines().map { it.toInt() }
    var partOne = 0
    var partTwo = 0
    for (i in 1 until input.size) {
        if (input[i] > input[i - 1]) {
            partOne++
        }
    }
    for (i in 3 until input.size) {
        if (input[i] + input[i - 1] + input[i - 2] > input[i - 1] + input[i - 2] + input[i - 3]) {
            partTwo++
        }
    }
    println("Part one: $partOne")
    println("Part two: $partTwo")
}