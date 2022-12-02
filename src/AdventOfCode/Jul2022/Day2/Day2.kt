package AdventOfCode.Jul2022.Day2

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2022/Day2/Input").readLines()
    input.sumOf { part1(it) }.also { println("Part one: $it") }
    input.sumOf { part2(it) }.also { println("Part two: $it") }
}

fun part1(input: String): Int {
    return when (input) {
        "A X" -> 4
        "A Y" -> 8
        "A Z" -> 3
        "B X" -> 1
        "B Y" -> 5
        "B Z" -> 9
        "C X" -> 7
        "C Y" -> 2
        "C Z" -> 6
        else -> {
            0
        }
    }
}
// A = stone = 1
// B = paper = 2
// C = scissors = 3
// X = lose = 0
// Y = draw = 3
// Z = win = 6
fun part2(input: String): Int {
    return when (input) {
        "A X" -> 3
        "A Y" -> 4
        "A Z" -> 8
        "B X" -> 1
        "B Y" -> 5
        "B Z" -> 9
        "C X" -> 2
        "C Y" -> 6
        "C Z" -> 7
        else -> {
            0
        }
    }
}