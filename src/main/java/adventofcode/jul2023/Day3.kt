package adventofcode.jul2023

import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .map { it.toCharArray() }
        .run {
            var x = 0
            var y = 0
            val adjacentNumbers = mutableListOf<Int>()

            while (getOrNull(y)?.getOrNull(x) is Char) {
                if (get(y)[x].isDigit()) {
                    val (isAdjacent, number) = findAdjacentNumber(y, x)
                    if (isAdjacent) adjacentNumbers.add(number)
                    x += number.toString().length
                }
                y += ++x / get(0).size
                x %= get(0).size
            }

            println("Part one: ${adjacentNumbers.sum()}")

            indices
                .flatMap { y -> get(0).indices.map { x -> y to x }.filter { (y, x) -> get(y)[x] == '*' } }
                .map { (y, x) ->
                    (-1..1)
                        .flatMap { i -> (-1..1).map { j -> y + i to x + j } }
                        .filter { (y, x) -> getOrNull(y)?.getOrNull(x)?.isDigit() == true }
                        .map { (y, x) ->
                            get(y)
                                .run {
                                    take(x).takeLastWhile { it.isDigit() } +
                                        drop(x).takeWhile { it.isDigit() }
                                }.joinToString("")
                                .toInt()
                        }.distinct()
                }.filter { it.size == 2 }
                .sumOf { it[0] * it[1] }
                .let { println("Part two: $it") }
        }
}

fun List<CharArray>.findAdjacentNumber(
    y: Int,
    x: Int,
): Pair<Boolean, Int> {
    val number = get(y).drop(x).takeWhile { it.isDigit() }.joinToString("")
    for (i in number.indices) {
        if (isAdjacent(y, x + i)) return true to number.toInt()
    }
    return false to number.toInt()
}

fun List<CharArray>.isAdjacent(
    y: Int,
    x: Int,
): Boolean {
    for (i in -1..1) {
        for (j in -1..1) {
            if (i == 0 && j == 0) continue
            val char = getOrNull(y + i)?.getOrNull(x + j)
            if (char != null && char != '.' && !char.isDigit()) return true
        }
    }
    return false
}
