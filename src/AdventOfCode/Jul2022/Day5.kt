package AdventOfCode.Jul2022

import java.io.File

fun main() {
    val gridPart1 = Array(9) { mutableListOf<Char>() }
    val gridPart2 = Array(9) { mutableListOf<Char>() }
    val input = File("src/Input").readLines()
    input.take(8).forEach {
        for (index in it.indices step 4) {
            val char = it.elementAt(index + 1)
            if (char.isLetter()) {
                gridPart1[index / 4].add(0, char)
                gridPart2[index / 4].add(0, char)
            }
        }
    }

    input.drop(10).forEach {
        it.split(" ")
          .filter { str -> str.first().isDigit() }
          .map { num -> num.toInt() }
          .also { op ->
              (0 until op[0]).map { gridPart1[op[1] - 1].removeLast() }.forEach { num -> gridPart1[op[2] - 1].add(num) }
              (0 until op[0]).map { gridPart2[op[1] - 1].removeLast() }.reversed().forEach { num -> gridPart2[op[2] - 1].add(num) }
          }
    }

    (0 until 9).map { gridPart1[it].last() }.also { println("Part one: ${it.joinToString("")}") }
    (0 until 9).map { gridPart2[it].last() }.also { println("Part two: ${it.joinToString("")}") }
}