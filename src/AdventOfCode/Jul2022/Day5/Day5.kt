package AdventOfCode.Jul2022.Day5

import java.io.File

var gridPart1 = Array(9) { mutableListOf<Char>() }
var gridPart2 = Array(9) { mutableListOf<Char>() }
fun main() {
    val input = File("src/AdventOfCode/Jul2022/Day5/Input").readLines()
    input.take(8).forEach {
        for (index in it.indices step 4) {
            val char = it.elementAt(index + 1)
            if (char.isLetter()) {
                gridPart1[index / 4].add(char)
                gridPart2[index / 4].add(char)
            }
        }
    }

    input.drop(10).forEach {
        it.filter { char -> char.isDigit() }
            .split("")
            .map { num -> num.toInt() }
            .also { op ->
                movePart1(op[0], op[1] - 1, op[2] - 1)
                movePart2(op[0], op[1] - 1, op[2] - 1)
            }
    }

    (0 until 9).map { gridPart1[it].last() }.also { println("Part one: ${it.joinToString("")}") }
    (0 until 9).map { gridPart2[it].last() }.also { println("Part two: ${it.joinToString("")}") }
}

fun movePart1(num: Int, fromCol: Int, toCol: Int) {
    (0 until num)
        .map { gridPart1[fromCol].removeLast() }
        .forEach { gridPart1[toCol].add(it) }
}

fun movePart2(num: Int, fromCol: Int, toCol: Int) {
    (0 until num)
        .map { gridPart2[fromCol].removeLast() }
        .reversed()
        .forEach { gridPart2[toCol].add(it) }
}